package InTechs.InTechs.chat.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatUpdateRequest {
    private String channelId;
    private String chatId;
    private String message;
}
