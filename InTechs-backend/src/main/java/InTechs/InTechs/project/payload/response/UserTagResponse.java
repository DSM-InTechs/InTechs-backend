package InTechs.InTechs.project.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTagResponse {
    private String email;
    private String name;
}
