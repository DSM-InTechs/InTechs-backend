package InTechs.InTechs.chat.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmojiRequest {
    private String channelId;
    private String chatId;
    private String emojiName;
}
