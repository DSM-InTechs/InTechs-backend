package InTechs.InTechs.chat.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChatRequest {

    private final String channelId;
    private final String message;

}
