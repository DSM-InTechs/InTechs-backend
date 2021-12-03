package InTechs.InTechs.channel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChannelIdResponse {

    private final String channelId;

}
