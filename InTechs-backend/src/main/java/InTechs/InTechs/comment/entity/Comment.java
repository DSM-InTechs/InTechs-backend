package InTechs.InTechs.comment.entity;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.user.entity.User;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "comment")
public class Comment {
    @Id
    private ObjectId id;
    private String content;
    private LocalDateTime createAt;
    @DBRef(lazy = true)
    private User user;
    @DBRef(lazy = true)
    private Issue issue;
}
