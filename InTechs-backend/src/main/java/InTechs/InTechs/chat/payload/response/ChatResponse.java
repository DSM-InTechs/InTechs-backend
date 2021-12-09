package InTechs.InTechs.chat.payload.response;

import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatResponse {
    private final String id;
    private final String message;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;
    private final Sender sender;
    private final Boolean isMine;
    private final boolean isDelete;
    private final ChatType chatType;
}
