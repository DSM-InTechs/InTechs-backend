package InTechs.InTechs.chat.payload.response;

import InTechs.InTechs.chat.entity.ChatType;
import InTechs.InTechs.chat.entity.Sender;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ChatResponse {
    private final String id;
    private final String message;
    private final String time;
    private final Sender sender;
    private final Boolean isMine;
    private final boolean isDelete;
    private final ChatType chatType;
    private final List<ThreadResponse> threads;
}
