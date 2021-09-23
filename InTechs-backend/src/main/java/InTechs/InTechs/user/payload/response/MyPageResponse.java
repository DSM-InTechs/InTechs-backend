package InTechs.InTechs.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class MyPageResponse {

    private final String name;
    private final String email;
    private final MultipartFile image;

}