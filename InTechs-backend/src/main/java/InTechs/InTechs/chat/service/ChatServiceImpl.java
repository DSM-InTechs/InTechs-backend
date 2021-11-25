package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChannelRepository channelRepository;
    private final FileUploader fileUploader;

    private final AuthenticationFacade authenticationFacade;

    private final SocketIOServer socketIOServer;


    @Override
    public List<ChatResponse> getChatList(String channelId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<Chat> chatList = chatRepository.findBySenderAndChannelId(user.getEmail(), channelId);

        List<ChatResponse> chatResponses = new ArrayList<>();
        for(Chat chat : chatList) {
            User sender = userRepository.findByEmail(chat.getSender().getEmail())
                    .orElseThrow(UserNotFoundException::new);

            chatResponses.add(
                    ChatResponse.builder()
                            .sender(SenderResponse.builder()
                                    .email(sender.getEmail())
                                    .image(sender.getFileUrl())
                                    .name(sender.getName())
                                    .build())
                            .message(chat.getMessage())
                            .id(channelId)
                            .isDelete(false)
                            .notice(false)
                            .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                            .build()
            );
        }

        return chatResponses;
    }

    @SneakyThrows
    @Override
    public void sendChat(User user, String channelId, String message) {
        boolean existsChannel = channelRepository.existsByChannelIdAndUsersContaining(channelId, user);

        if(!existsChannel) throw new ChatChannelNotFoundException();

        Chat chat = Chat.builder()
                .sender(Sender.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .image(user.getFileUrl()).build())
                .message(message)
                .channelId(channelId)
                .isDeleted(false)
                .notice(false)
                .chatType(ChatType.TEXT)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);

        channel.addChat(chat);
        channelRepository.save(channel);

        chatRepository.save(chat);
    }

    @SneakyThrows
    public void sendFile(User user, String channelId, MultipartFile file) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
        String fileName = UUID.randomUUID().toString();

        Sender sender = Sender.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getFileUrl())
                .build();
        Chat chat = Chat.builder()
                .sender(sender)
                .message(fileName)
                .channelId(channelId)
                .isDeleted(false)
                .notice(false)
                .chatType(ChatType.FILE)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        socketIOServer.getRoomOperations(channelId).sendEvent("send-file", chat);


        fileUploader.uploadFile(file, fileName);
        channel.addChat(chat);
        chatRepository.save(chat);
    }

    @SneakyThrows
    public void sendImage(User user, String channelId, MultipartFile file) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
        String fileName = UUID.randomUUID().toString();

        Sender sender = Sender.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getFileUrl())
                .build();
        Chat chat = Chat.builder()
                .sender(sender)
                .message(fileName)
                .channelId(channelId)
                .isDeleted(false)
                .notice(false)
                .chatType(ChatType.IMAGE)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        socketIOServer.getRoomOperations(channelId).sendEvent("send-image", chat);


        fileUploader.uploadFile(file, fileName);
        channel.addChat(chat);
        chatRepository.save(chat);
    }
}
