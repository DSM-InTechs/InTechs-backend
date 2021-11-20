package InTechs.InTechs.chat.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(of = "email")
public class Sender {
    private String email;
    private String name;
    private String image;
}
