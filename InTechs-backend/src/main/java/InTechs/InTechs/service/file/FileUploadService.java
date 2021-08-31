package InTechs.InTechs.service.file;

import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;
import InTechs.InTechs.service.file.S3Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final S3Service s3Service;

    public String uploadImage(MultipartFile file, String folder){
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream = file.getInputStream()){
            s3Service.uploadFile(inputStream, objectMetadata, fileName, folder);
        } catch(IOException e){
            throw new IllegalArgumentException(String.format("Error during file conversion (%s)", file.getOriginalFilename()));
        }
        return s3Service.getFileUrl(fileName);
    }

    private String createFileName(String originalFileName){
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch(StringIndexOutOfBoundsException e){
            throw new BaseException(ExceptionMessage.INVALID_FILE_TYPE);
        }
    }

    // image resize
    public String imageResizeAndUpload(MultipartFile file) {
        String miniFolder = "/project/mini";
        String fileName = createFileName(file.getOriginalFilename());
        try{
            BufferedImage bufferImage = ImageIO.read(file.getInputStream());
            BufferedImage thumbnailImage = Thumbnails.of(bufferImage).size(100,100).asBufferedImage(); // url 넣어도 됨

            ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
            String imageType = file.getContentType(); // 파일 타입!
            ImageIO.write(thumbnailImage, imageType.substring(imageType.indexOf("/")+1), thumbOutput);

            // set metadata
            ObjectMetadata thumbObjectMetadata = new ObjectMetadata();
            byte[] thumbBytes = thumbOutput.toByteArray();
            thumbObjectMetadata.setContentLength(thumbBytes.length);
            thumbObjectMetadata.setContentType(file.getContentType());

            // save in s3
            InputStream thumbInput = new ByteArrayInputStream(thumbBytes);
            s3Service.uploadFile(thumbInput, thumbObjectMetadata, fileName, miniFolder);
            thumbOutput.close();
            thumbInput.close();
        } catch(Exception e){
            throw new BaseException(ExceptionMessage.FILE_SIZE_CONVERSION_FAIL);
        }
        return s3Service.getFileUrl(fileName);
    }
}
