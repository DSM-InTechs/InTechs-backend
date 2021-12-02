package InTechs.InTechs.channel.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChannelInfoResponse {
    private final String id;
    private final String name;
    private final String image;
    private final boolean isDm;
    private final boolean isNotification;
    private final List<ChannelUser> users;
}
