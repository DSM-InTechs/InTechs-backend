package InTechs.InTechs.issue.entity;

import InTechs.InTechs.comment.entity.Comment;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "issue")
public class Issue {
    @Id
    private ObjectId id;
    private String writer;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private State state;
    @Setter
    private int progress = 0;
    @Setter
    private String endDate;
    private int projectId;
    @Setter
    @DBRef(lazy = true)
    private List<User> users = new ArrayList<>(); // 수정 가능하게 변경
    @Setter
    @DBRef(lazy = true)
    private List<Comment> comments = new ArrayList<>();
    @Setter
    private Set<Tag> tags = new HashSet<>();

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
