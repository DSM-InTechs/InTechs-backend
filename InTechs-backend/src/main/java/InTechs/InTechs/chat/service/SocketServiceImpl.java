package InTechs.InTechs.chat.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.payload.request.ChatRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.ErrorResponse;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.exception.exceptions.ChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.FirebaseException;
import InTechs.InTechs.notification.NotificationService;
import InTechs.InTechs.security.JwtTokenProvider;
import InTechs.InTechs.user.entity.ChannelUser;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService {

    private final JwtTokenProvider jwtTokenProvider;

    private final NotificationService notificationService;
    private final ChatService chatService;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    private final SocketIOServer server;

    @Override
    public void connect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if (!jwtTokenProvider.validateToken(token)) {
            clientDisconnect(client, 403, "Can not resolve token");
            return;
        }

        User user;
        try {
            user = userRepository.findByEmail(jwtTokenProvider.getEmail(token))
                    .orElseThrow(RuntimeException::new);
            client.set("user", user);
        } catch (Exception e) {
            clientDisconnect(client, 404, "Could not get user");
            return;
        }
    }

    @Override
    public void disConnect(SocketIOClient client) {
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s%n", client.getSessionId())
        );
    }

    @Override
    public void joinChannel(SocketIOClient client, String channelId) {
        User user = client.get("user");

        if(user == null) {
            clientDisconnect(client, 403, "Invalid Connection");
            return;
        }

        boolean exitsUser = channelRepository.existsByChannelIdAndUsersContaining(channelId, user);
        if(!exitsUser) {
            clientDisconnect(client, 401, "Invalid Room");
            return;
        }

        client.joinRoom(channelId);
        printLog(
                client,
                String.format("Join Room [senderId(%s) -> Channel Id(%s)] Session Id: %s%n",
                        user.getEmail(), channelId, client.getSessionId())
        );
    }

    @Override
    public void chat(SocketIOClient client, ChatRequest chatRequest) {
        if(!client.getAllRooms().contains(chatRequest.getChannelId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        User user = client.get("user");
        chatService.sendChat(
                user,
                chatRequest.getChannelId(),
                chatRequest.getMessage()
        );

        server.getRoomOperations(chatRequest.getChannelId()).sendEvent(
                "send",
                ChatResponse.builder()
                .sender(SenderResponse.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .image(user.getFileUrl()).build())
                .message(chatRequest.getMessage())
                .id(chatRequest.getChannelId())
                .isDelete(false)
                .notice(false)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build()

        );

        Channel channel = channelRepository.findById(chatRequest.getChannelId()).orElseThrow(ChannelNotFoundException::new);
        List<String> targetTokens = channel.getChannelUsers().stream().filter(ChannelUser::isNotificationAllow).map(tu -> tu.getUser().getTargetToken()).collect(Collectors.toList());

        try {
            notificationService.sendTargetsMessage(targetTokens, "Intechs 메세지가 왔습니다.", chatRequest.getMessage(),user.getFileUrl());
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new FirebaseException();
        }
    }

    @SneakyThrows
    private void printLog(SocketIOClient client, String content) {
        String stringDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.printf(
                "%s  %s - [%s] - %s",
                stringDate,
                "SOCKET",
                client.getRemoteAddress().toString().substring(1),
                content
        );
    }

    private void clientDisconnect(SocketIOClient client, Integer status, String reason) {
        client.sendEvent("ERROR", new ErrorResponse(status, reason));
        client.disconnect();
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s - %s%n", client.getSessionId(), reason)
        );
    }

}
