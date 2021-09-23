package InTechs.InTechs.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
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

    private String image;

    private boolean isActive;

    public User setActive(boolean isActive) {
        this.isActive = isActive;

        return this;
    }

    public User setImage(String image) {
        this.image = image;

        return  this;
    }
}