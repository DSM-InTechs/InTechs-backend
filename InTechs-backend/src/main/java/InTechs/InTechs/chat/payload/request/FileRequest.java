package InTechs.InTechs.chat.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class FileRequest {

    private final String chatType;
    private final MultipartFile file;

}
