package InTechs.InTechs.payload.request.issue;

import InTechs.InTechs.entity.State;
import lombok.Getter;

@Getter
public class IssueCreateRequest {
    private String title;
    private String content;
    private State state;
    private int progress;
    private String end_date;
}
