package InTechs.InTechs.chat.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatResponse {

    private final int chatId;

    private final int channelId;

    private final String sender;

    private final String message;

    private final Boolean isMine;

    private final String imageName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;

}
