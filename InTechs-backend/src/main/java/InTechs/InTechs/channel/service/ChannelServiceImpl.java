package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.ChannelUser;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private final FileUploader fileUploader;

    @Override
    public void createChannel(int projectId, ChannelRequest channelRequest) {
        String channelId = UUID.randomUUID().toString();

        ChannelUser channelUser = ChannelUser.builder()
                .user(findUser())
                .build();

        Channel channel = Channel.builder()
                .projectId(projectId)
                .channelId(channelId)
                .name(channelRequest.getName())
                .users(Collections.singletonList(channelUser))
                .build();

        channelRepository.save(channel);
    }

    @Override
    public void updateChannel(String channelId, ChannelRequest channelRequest) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channelRepository.save(channel.updateName(channelRequest.getName()));
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
                       .name(user.getUser().getName())
                       .email(user.getUser().getEmail())
                       .image(imageUrl(user.getUser().getFileName()))
               .build()).collect(Collectors.toList());
    }

    @Override
    public void AddUser(String targetEmail, String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        User target = userRepository.findByEmail(targetEmail)
                .orElseThrow(UserNotFoundException::new);

        channel.addUser(ChannelUser.builder().user(target).build());
        channelRepository.save(channel);
    }

    @Override
    public void exitChannel(String channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(ChatChannelNotFoundException::new);

        channel.deleteUser(ChannelUser.builder().user(findUser()).build());
        channelRepository.save(channel);
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
