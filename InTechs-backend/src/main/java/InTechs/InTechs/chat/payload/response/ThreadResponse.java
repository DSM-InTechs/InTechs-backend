package InTechs.InTechs.chat.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ThreadResponse {
    private String chatId;

    private String message;

    private SenderResponse sender;

    private LocalDateTime time;
}
