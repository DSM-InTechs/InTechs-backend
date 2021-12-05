package InTechs.InTechs.chat.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmojiRequest {
    private String chatId;
    private String emojiName;
    private boolean action; // true면 create, false면 delete
}
