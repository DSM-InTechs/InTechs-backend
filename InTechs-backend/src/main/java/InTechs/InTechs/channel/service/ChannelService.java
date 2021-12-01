package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.payload.response.ChannelResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;

import java.io.IOException;
import java.util.List;

public interface ChannelService {

    void createChannel(int projectId, ChannelRequest channelRequest);

    void createDirectMessage(int projectId, String email);

    void updateChannel(int projectId, String channelId, ChannelRequest channelRequest) throws IOException;

    void deleteChannel(int projectId, String channelId);

    List<ProfileResponse> getProfiles(int projectId, String channelId);

    void AddUser(int projectId, String targetEmail, String channelId);

    void exitChannel(int projectId, String channelId);

    List<ChannelResponse> getChannels(int projectId);

}
