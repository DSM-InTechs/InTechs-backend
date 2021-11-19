package InTechs.InTechs.channel.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponse {
    final private String id;
    final private String message;
    final private LocalDateTime time;
    final private String sender;
    final private boolean notice;
}
