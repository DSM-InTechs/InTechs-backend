package InTechs.InTechs.chat.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatDeleteRequest {
    private final String channelId;
    private final String messageId;
}
