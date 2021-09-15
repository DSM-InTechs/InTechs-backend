package InTechs.InTechs.issue.payload.response;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class IssueFilterResponse {
    private String id;
    private String writer;
    private String title;
    private String content;
    private State state;
    private int progress;
    private String end_date;
    private int projectId;
    private List<String> usersId;
    private Set<Tag> tags;
}
