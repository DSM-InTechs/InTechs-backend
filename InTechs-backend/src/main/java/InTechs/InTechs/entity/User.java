package InTechs.InTechs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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