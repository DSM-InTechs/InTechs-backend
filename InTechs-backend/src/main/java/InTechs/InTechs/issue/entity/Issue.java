package InTechs.InTechs.issue.entity;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.entity.Project;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "issue")
public class Issue {
    @Id
    private ObjectId id;
    private String writer; // 이슈 생성자
    private String title;
    @Setter
    private String content;
    @Setter
    private State state;
    @Setter
    private int progress; // 퍼센트 에이지
    @Setter
    private String end_date;
    @DBRef(lazy = true)
    private Project project;
    @Setter
    private Set<Tag> tags;
}
