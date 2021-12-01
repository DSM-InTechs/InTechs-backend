package InTechs.InTechs.channel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChannelResponse {

    private final String id;

    private final String name;

    private final String image;

    private final String message;

    private final boolean isDm;

    private final LocalDateTime time;

}
