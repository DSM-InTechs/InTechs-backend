package InTechs.InTechs.chat.payload.response;

import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String id;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private Sender sender;
    private Boolean isMine;
    private boolean isDelete;
    private ChatType chatType;
}
