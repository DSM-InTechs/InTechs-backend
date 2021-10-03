package InTechs.InTechs.project.payload.response;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserIssueResponse {
    private String email;
    private String name;
}
