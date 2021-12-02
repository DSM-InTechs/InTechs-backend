package InTechs.InTechs.chat.payload.request;

import lombok.Getter;

@Getter
public class ThreadRequest {
    private String chatId;
    private String message;
}
