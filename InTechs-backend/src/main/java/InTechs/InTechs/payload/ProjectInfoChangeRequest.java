package InTechs.InTechs.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ProjectInfoChangeRequest {
    private String name;
    private MultipartFile image;
}
