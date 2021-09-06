package InTechs.InTechs.issue.entity;

import InTechs.InTechs.issue.value.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "issue")
public class Issue {
    @Id
    private int id;
    private String title;
    private String content;
    private State state;
    private int progress; // 퍼센트 에이지
    private String end_date;
}
