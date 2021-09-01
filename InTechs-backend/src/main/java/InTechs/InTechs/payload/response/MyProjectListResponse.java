package InTechs.InTechs.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MyProjectListResponse {

    private final int id;
    private final String name;
    private final MultipartFile image;
    private final LocalDate createdAt;

}
