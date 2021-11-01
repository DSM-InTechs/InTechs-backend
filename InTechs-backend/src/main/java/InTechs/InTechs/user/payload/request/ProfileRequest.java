package InTechs.InTechs.user.payload.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class ProfileRequest {

    private final String name;
    private final MultipartFile image;

}
