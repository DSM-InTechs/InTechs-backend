package InTechs.InTechs.issue.payload;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IssueCreateRequest {
    private String title;
    private String content;
    private State state;
    private int progress;
    private String end_date;
    private List<Tag> tags;
}
