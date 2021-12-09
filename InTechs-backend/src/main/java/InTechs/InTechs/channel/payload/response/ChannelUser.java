package InTechs.InTechs.channel.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChannelUser {
    private final String email;
    private final String name;
    private final String image;
    private final boolean isActive;
}
