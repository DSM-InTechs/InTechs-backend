package InTechs.InTechs.chat.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmojiResponse {
    String emoji;
    EmojiInfoResponse emojiInfo;
}
