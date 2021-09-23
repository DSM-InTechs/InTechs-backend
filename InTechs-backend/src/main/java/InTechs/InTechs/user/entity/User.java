package InTechs.InTechs.user.entity;

import InTechs.InTechs.project.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Document(collection = "user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Email
    private String email;

    @NotBlank
    @Size(max = 64)
    private String password;

    @NotBlank
    @Size(max = 15)
    private String name;

    @Size(max = 100)
    private String image;

    private boolean isActive;

    @DBRef(lazy = true)
    private List<Project> project;

    public User setActive(boolean isActive) {
        this.isActive = isActive;

        return this;
    }

    public User setImage(String image) {
        this.image = image;

        return  this;
    }

    public User setName(String name) {
        this.name = name;

        return this;
    }
}
