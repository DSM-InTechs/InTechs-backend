package InTechs.InTechs.chat.payload.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Sender {
    private String email;
    private String name;
    private String image;
}
