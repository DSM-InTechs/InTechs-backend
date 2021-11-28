package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.payload.response.ChannelResponse;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.ChannelUser;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static InTechs.InTechs.chat.entity.ChatType.INFO;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    private final AuthenticationFacade authenticationFacade;

    private final FileUploader fileUploader;

    private final SocketIOServer server;

    @Value("${image.user}")
    private String baseImage;

    @Override
    public void createChannel(int projectId, ChannelRequest channelRequest) {
        User user = findUser();
        String channelId = UUID.randomUUID().toString();

        Channel channel = Channel.builder()
                .projectId(projectId)
                .channelId(channelId)
                .name(channelRequest.getName())
                .fileUrl(baseImage)
                .users(Collections.singletonList(user))
                .notificationOnUsers(Collections.singletonList(findUser()))
                .build();

        channelRepository.save(channel);
    }

    @Override
    public void updateChannel(String channelId, ChannelRequest channelRequest) throws IOException {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        MultipartFile file = channelRequest.getFileUrl();
        String fileName = UUID.randomUUID().toString();

        fileUploader.uploadFile(file, fileName);

        String fileUrl = fileUploader.getObjectUrl(fileName);

        channelRepository.save(channel.updateName(channelRequest.getName()));
        channelRepository.save(channel.updateFileUrl(fileUrl));
    }

    @Override
    public void deleteChannel(String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channelRepository.delete(channel);
    }

    @Override
    public List<ProfileResponse> getProfiles(String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

       return channel.getUsers().stream()
               .map(user -> ProfileResponse.builder()
                       .name(user.getName())
                       .email(user.getEmail())
                       .image(user.getFileUrl())
               .build()).collect(Collectors.toList());
    }

    @Override
    public void AddUser(String targetEmail, String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        User target = userRepository.findByEmail(targetEmail)
                .orElseThrow(UserNotFoundException::new);

        String addMessage = target.getName() + "님이 입장하셨습니다.";

        channel.addUser(target);
        channelRepository.save(channel);

        server.getRoomOperations(channelId).sendEvent(
                "addUser",
                addMessage);

        Chat chat = Chat.builder()
                .message(addMessage)
                .channelId(channelId)
                .time(LocalDateTime.now())
                .chatType(ChatType.INFO)
                .build();

        chatRepository.save(chat);

    }

    @Override
    public void exitChannel(String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channel.deleteUser(findUser());
        channelRepository.save(channel);
    }

    @Override
    public List<ChannelResponse> getChannels() {
        List<Channel> channels = channelRepository.findByUsersContains(findUser());
        List<ChannelResponse> channelResponses = new ArrayList<>();

        for(Channel channel : channels) {
            Optional<Chat> chat = chatRepository.findTopByChannelIdOrderByTime(channel.getChannelId());
            String lastChat = chat.map(Chat::getMessage).orElseThrow(ChatChannelNotFoundException::new);

            channelResponses.add(
                    ChannelResponse.builder()
                            .id(channel.getChannelId())
                            .name(channel.getName())
                            .image(channel.getFileUrl())
                            .message(lastChat)
                            .build()
            );
        }
        return channelResponses;
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

}
