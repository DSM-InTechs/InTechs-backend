package InTechs.InTechs.project.payload.response;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String name;
}
