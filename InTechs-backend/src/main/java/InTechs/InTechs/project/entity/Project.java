package InTechs.InTechs.project.entity;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.user.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private LocalDateTime createAt;
    
    @DBRef(lazy = true)
    private List<Issue> issues = new ArrayList<>();
    private Set<Tag> tags = new HashSet<>();

    @DBRef(lazy = true)
    private List<Channel> channels;

    @DBRef(lazy = true)
    private List<Chat> chats;

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        boolean b = this.users.remove(user);
        this.users.remove(user);
    }

    public void addIssue(Issue issue){
        this.issues.add(issue);
    }
  
    public void addTags(Set<Tag> tags){
        this.tags.addAll(tags);
    }
}