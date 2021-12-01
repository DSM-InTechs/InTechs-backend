package InTechs.InTechs.chat.payload.request;

import InTechs.InTechs.chat.entity.ChatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextRequest {

    private String channelId;
    private String message;

}
