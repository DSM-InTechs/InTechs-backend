package InTechs.InTechs.user.entity;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.project.entity.Project;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@EqualsAndHashCode(exclude = "email")
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

    private String fileUrl;

    private Boolean isActive;

    @DBRef(lazy = true)
    private List<Project> project;

    @DBRef(lazy = true)
    private List<Channel> channels;

    private String targetToken;

    public User updateActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void updateTargetToken(String targetToken){
        this.targetToken = targetToken;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    public User updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

}
