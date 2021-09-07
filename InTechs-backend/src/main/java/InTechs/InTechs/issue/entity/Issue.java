package InTechs.InTechs.issue.entity;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private String content;
    private State state;
    private int progress; // 퍼센트 에이지
    private String end_date;
    private List<Tag> tags;
}
