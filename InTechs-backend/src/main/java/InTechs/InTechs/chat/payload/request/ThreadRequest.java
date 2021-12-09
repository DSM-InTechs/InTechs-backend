package InTechs.InTechs.chat.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThreadRequest {
    private String channelId;
    private String chatId;
    private String message;
    private List<String> mentionUsers;
}
