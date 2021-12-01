package InTechs.InTechs.chat.payload.response;

import InTechs.InTechs.chat.entity.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSendResponse {

    private String id;

    private String message;

    private SenderResponse sender;

    private Boolean isDelete;

    private Boolean notice;

    private ChatType chatType;

    private LocalDateTime time;

    public String getTime() {
        return time.toString();
    }

}
