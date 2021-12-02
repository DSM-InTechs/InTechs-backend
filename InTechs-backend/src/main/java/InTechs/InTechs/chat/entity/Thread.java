package InTechs.InTechs.chat.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Thread {
    private final String message;
    private final Sender sender;
    private final LocalDateTime time;
}
