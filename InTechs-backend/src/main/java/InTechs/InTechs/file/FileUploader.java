package InTechs.InTechs.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploader {
    String uploadFile(MultipartFile file, String fileName) throws IOException;
    File getFile(String fileName) throws IOException;

    String getObjectUrl(String fileName);
}
