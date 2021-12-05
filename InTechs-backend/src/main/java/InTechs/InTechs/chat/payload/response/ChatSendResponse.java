package InTechs.InTechs.chat.payload.response;

import InTechs.InTechs.chat.entity.ChatType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noticeTime;

    private List<ThreadResponse> threads;

    public String getTime() {
        return time.toString();
    }

    public String getNoticeTime() {
        return noticeTime.toString();
    }

}
