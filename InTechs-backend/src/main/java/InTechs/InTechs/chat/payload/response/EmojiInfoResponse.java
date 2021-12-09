package InTechs.InTechs.chat.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class EmojiInfoResponse {
    int count;
    Set<SenderResponse> users;
}
