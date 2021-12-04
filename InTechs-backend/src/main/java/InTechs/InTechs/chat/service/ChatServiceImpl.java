package InTechs.InTechs.chat.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.payload.request.FileRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class  ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChannelRepository channelRepository;
    private final FileUploader fileUploader;

    private final AuthenticationFacade authenticationFacade;

    private final SocketIOServer socketIOServer;


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

        channelRepository.save(channel);
        chatRepository.save(chat);
    }

    @SneakyThrows
    @Override
    public void sendFile(String channelId, FileRequest fileRequest) {
        String fileName = UUID.randomUUID().toString();
        ChatType chatType = ChatType.valueOf(fileRequest.getChatType());

        fileUploader.uploadFile(fileRequest.getFile(), fileName);
        String fileUrl = fileUploader.getObjectUrl(fileName);

        Sender sender = Sender.builder()
                .email(findUser().getEmail())
                .name(findUser().getName())
                .image(findUser().getFileUrl())
                .build();
        Chat chat = Chat.builder()
                .sender(sender)
                .message(fileUrl)
                .channelId(channelId)
                .isDeleted(false)
                .notice(false)
                .chatType(chatType)
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        socketIOServer.getRoomOperations(channelId).sendEvent("send-file", chat);
        chatRepository.save(chat);
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }
}
