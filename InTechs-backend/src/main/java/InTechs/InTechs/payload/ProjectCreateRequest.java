package InTechs.InTechs.payload;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class ProjectCreateRequest {
    private String name;
    private MultipartFile image;
}
