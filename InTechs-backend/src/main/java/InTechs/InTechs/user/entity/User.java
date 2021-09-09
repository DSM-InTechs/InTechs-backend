package InTechs.InTechs.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collection = "user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String email;

    private String password;

    private String name;

    private MultipartFile image;

    private boolean isActive;

    public User setActive(boolean isActive) {
        this.isActive = isActive;

        return this;
    }

    public User setImage(MultipartFile image) {
        this.image = image;

        return  this;
    }

    public User setName(String name) {
        this.name = name;

        return this;
    }
}
