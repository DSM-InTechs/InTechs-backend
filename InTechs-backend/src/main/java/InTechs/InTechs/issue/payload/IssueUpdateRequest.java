package InTechs.InTechs.issue.payload;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.Getter;
import java.util.Set;

@Getter
public class IssueUpdateRequest {
    private String title;
    private String content;
    private State state;
    private int progress;
    private String end_date;
    private Set<Tag> tags;
}
