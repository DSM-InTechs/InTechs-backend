package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.user.payload.response.ProfileResponse;

import java.util.List;

public interface ChannelService {

    void updateChannel(String channelId, ChannelRequest channelRequest);

    void createChannel(int projectId, ChannelRequest channelRequest);

    void deleteChannel(String channelId);

    List<ProfileResponse> getProfiles(String channelId);

    void AddUser(String targetEmail, String channelId);

    void exitChannel(String channelId);

}
