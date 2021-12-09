package InTechs.InTechs.chat.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.entity.Thread;
import InTechs.InTechs.chat.payload.request.FileRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.payload.response.ChatSendResponse;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.payload.response.ThreadResponse;
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
import java.util.ArrayList;
import java.util.List;
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
        List<Thread> threadResponses = new ArrayList<>();

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
                .noticeTime(LocalDateTime.now())
                .threads(threadResponses)
                .build();

        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);

        channelRepository.save(channel);
        chatRepository.save(chat);
    }

    @SneakyThrows
    @Override
    public void sendFile(String channelId, FileRequest fileRequest) {
        ChatType chatType = ChatType.valueOf(fileRequest.getChatType());
        String fileName = fileRequest.getFile().getOriginalFilename();
        
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
                .noticeTime(LocalDateTime.now())
                .threads(null)
                .build();

        chatRepository.save(chat);
        
        socketIOServer.getRoomOperations(channelId).sendEvent(
                "send-file",
                ChatSendResponse.builder()
                        .id(chat.getId().toString())
                        .sender(SenderResponse.builder()
                                .email(findUser().getEmail())
                                .name(findUser().getName())
                                .image(findUser().getFileUrl()).build())
                        .message(chat.getMessage())
                        .chatType(chatType)
                        .isDelete(false)
                        .notice(false)
                        .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .noticeTime(LocalDateTime.now())
                        .noticeTime(LocalDateTime.now())
                        .threads(threadResponsesCreate(chat.getThreads()))
                        .build());
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    private List<ThreadResponse> threadResponsesCreate(List<Thread> threads){
        List<ThreadResponse> threadResponses = new ArrayList<>();
        if(threads==null){
            threadResponses.add(ThreadResponse.builder().build());
            return threadResponses;
        }
        for(Thread t : threads){
            threadResponses.add(
                    ThreadResponse.builder()
                            .message(t.getMessage())
                            .sender(SenderResponse.builder().email(t.getSender().getEmail()).name(t.getSender().getName()).image(t.getSender().getImage()).build())
                            .time(t.getTime()).build()
            );
        }
        return threadResponses;
    }
}
