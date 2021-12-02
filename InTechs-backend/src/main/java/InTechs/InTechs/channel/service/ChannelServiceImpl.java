package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.payload.response.ChannelInfoResponse;
import InTechs.InTechs.channel.payload.response.ChannelResponse;
import InTechs.InTechs.channel.payload.response.ChannelUser;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
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
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
                .isDM(false)
                .users(Collections.singletonList(user))
                .notificationOnUsers(Collections.singletonList(user))
                .time(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                .build();

        channelRepository.save(channel);
        addUser(user, channelId);
    }

    @Override
    public void updateChannel(int projectId, String channelId, ChannelRequest channelRequest) throws IOException {
        Channel channel = channelRepository.findByProjectIdAndChannelId(projectId, channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        MultipartFile file = channelRequest.getFileUrl();
        String fileName = UUID.randomUUID().toString();

        fileUploader.uploadFile(file, fileName);

        String fileUrl = fileUploader.getObjectUrl(fileName);

        channelRepository.save(channel.updateName(channelRequest.getName()));
        channelRepository.save(channel.updateFileUrl(fileUrl));
    }

    @Override
    public void deleteChannel(int projectId, String channelId) {
        Channel channel = channelRepository.findByProjectIdAndChannelId(projectId, channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channelRepository.delete(channel);
    }

    @Override
    public List<ProfileResponse> getProfiles(int projectId, String channelId) {
        Channel channel = channelRepository.findByProjectIdAndChannelId(projectId, channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

       return channel.getUsers().stream()
               .map(user -> ProfileResponse.builder()
                       .name(user.getName())
                       .email(user.getEmail())
                       .image(user.getFileUrl())
               .build()).collect(Collectors.toList());
    }

    @Override
    public void AddUser(int projectId, String targetEmail, String channelId) {
        Channel channel = channelRepository.findByProjectIdAndChannelId(projectId, channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        User target = userRepository.findByEmail(targetEmail)
                .orElseThrow(UserNotFoundException::new);

        channel.addUser(target);
        channel.notificationOn(target);
        channelRepository.save(channel);

        addUser(target, channelId);
    }

    @Override
    public void exitChannel(int projectId, String channelId) {
        Channel channel = channelRepository.findByProjectIdAndChannelId(projectId, channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channel.deleteUser(findUser());
        channelRepository.save(channel);

        String exitMessage = findUser().getName() + "님이 퇴장하셨습니다.";

        server.getRoomOperations(channelId).sendEvent(
                "exitUser",
                exitMessage);

        Chat chat = Chat.builder()
                .sender(Sender.builder()
                        .email(findUser().getEmail())
                        .name(findUser().getName())
                        .image(findUser().getFileUrl())
                        .build())
                .message(exitMessage)
                .channelId(channelId)
                .time(LocalDateTime.now())
                .chatType(ChatType.INFO)
                .build();

        chatRepository.save(chat);
    }

    @Override
    public List<ChannelResponse> getChannels(int projectId) {
        User user = findUser();
        List<Channel> channels = channelRepository.findByProjectIdAndUsersContains(projectId, findUser());
        List<ChannelResponse> channelResponses = new ArrayList<>();

        for(Channel channel : channels) {
            Chat chat = chatRepository.findTop1ByChannelIdOrderByTime(channel.getChannelId())
                    .orElse(Chat.builder()
                                .message("")
                                .time(null).build());

            String channelName = channel.getName();
            String imageUrl = channel.getFileUrl();
            List<User> users = channel.getUsers();
            boolean dm = channel.isDM();
            if (users.size() == 2) {
                User targetUser = users.stream()
                        .filter(u -> !u.equals(user))
                        .findFirst()
                        .orElseThrow(UserNotFoundException::new);

                channelName = targetUser.getName();
                imageUrl = targetUser.getFileUrl();
                dm = true;
            }
            channelResponses.add(
                    ChannelResponse.builder()
                            .id(channel.getChannelId())
                            .name(channelName)
                            .image(imageUrl)
                            .message(chat.getMessage())
                            .time(chat.getTime())
                            .isDm(dm)
                            .build()
            );
        }
        return channelResponses;
    }

    @Override
    public ChannelInfoResponse channelInfo(String channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
        return ChannelInfoResponse.builder()
                    .id(channel.getChannelId())
                    .name(channel.getName())
                    .image(channel.getFileUrl())
                    .isDm(channel.isDM())
                    .isNotification(notificationCheck(channel.getNotificationOnUsers()))
                    .users(channelUserList(channel.getUsers()))
                    .build();
    }

    private List<ChannelUser> channelUserList(List<User> users){
        List<ChannelUser> channelUsers = new ArrayList<>();
        for(User u : users){
            channelUsers.add(ChannelUser.builder()
                                .email(u.getEmail())
                                .name(u.getName())
                                .image(u.getFileUrl())
                                .isActive(u.getIsActive()).build());
        }
        return channelUsers;
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    private void addUser(User user, String channelId) {
        String addMessage = user.getName() + "님이 입장하셨습니다.";

        server.getRoomOperations(channelId).sendEvent(
                "addUser",
                addMessage);

        Chat chat = Chat.builder()
                .sender(Sender.builder()
                        .email(user.getEmail())
                        .name(user.getName())
                        .image(user.getFileUrl())
                        .build())
                .message(addMessage)
                .channelId(channelId)
                .time(LocalDateTime.now())
                .chatType(ChatType.INFO)
                .build();

        chatRepository.save(chat);
    }

    private boolean notificationCheck(List<User> users){
        User user = userRepository.findById(authenticationFacade.getUserEmail()).orElseThrow(UserNotFoundException::new);
        return users.contains(user);
    }
}
