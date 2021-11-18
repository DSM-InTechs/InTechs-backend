package InTechs.InTechs.channel.payload.response;

import lombok.Builder;

import java.util.List;

@Builder
public class ChatsResponse {
    private List<ChatResponse> chats;
    private String channelId;
}
