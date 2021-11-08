package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Channel;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.payload.request.ChannelRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.repository.ChannelRepository;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.file.S3FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChannelRepository channelRepository;

    private final AuthenticationFacade authenticationFacade;
    private final FileUploader fileUploader;

    @Override
    public List<ChatResponse> getChatList(String channelId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        List<Chat> chatList = chatRepository.findBySenderAndChannelId(user.getEmail(), channelId);

        List<ChatResponse> chatResponses = new ArrayList<>();
        for(Chat chat : chatList) {
            User sender = userRepository.findByEmail(chat.getSender())
                    .orElseThrow(UserNotFoundException::new);

            chatResponses.add(
                    ChatResponse.builder()
                            .sender(sender.getName())
                            .message(chat.getMessage())
                            .isMine(user.getEmail().equals(sender.getEmail()))
                            .imageName(imageUrl(findUser().getFileName()))
                            .build()
            );
        }

        return chatResponses;
    }

    @SneakyThrows
    @Override
    public void sendChat(User user, String channelId, String message) {
        boolean existsChannel = channelRepository.existsByChannelIdAndUsers(channelId, user);

        if(!existsChannel) throw new ChatChannelNotFoundException();

        chatRepository.save(
                Chat.builder()
                        .sender(user.getEmail())
                        .message(message)
                        .channelId(channelId)
                .build()
        );
    }

    @Override
    public void updateChannel(String channelId, ChannelRequest channelRequest) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channelRepository.save(channel.updateName(channelRequest.getNewName()));
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    private String imageUrl(String fileName) {
        String fileUrl = fileUploader.getObjectUrl(fileName);

        if(fileUrl == null) {
            fileUrl = fileUploader.getObjectUrl("인덱스 프로필.jpg");
        }
        return fileUrl;
    }
}
