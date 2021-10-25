package InTechs.InTechs.project.payload.response;

import InTechs.InTechs.project.value.Image;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProjectInfoResponse {
    private String name;
    private Image image;
}
