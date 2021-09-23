package InTechs.InTechs.project.payload.response;

import lombok.*;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class ProjectUserResponse {
    private final String email;
    private final String name;
    private final String imageUri;
    private final boolean isActive;
}
