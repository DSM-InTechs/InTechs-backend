package InTechs.InTechs.chat.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatsResponse {
    final private List<ChatResponse> chats;
    final private String channelId;
    final private ChatResponse notice;
}
