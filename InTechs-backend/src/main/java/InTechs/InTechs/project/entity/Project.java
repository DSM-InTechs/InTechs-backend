package InTechs.InTechs.project.entity;

import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.user.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
@Document(collection = "project")
public class Project {
    @Id
    @NotNull
    @Length(min = 6, max = 6)
    private int number;
    @NotBlank
    @Size(max = 26)
    private String name;
    private Image image;
    @DBRef(lazy = true)
    private List<User> users;

    private LocalDate createAt;

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        boolean b = this.users.remove(user);
    }
}