package InTechs.InTechs.issue.payload;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Builder
@Getter
public class IssueCreateRequest {
    @NotBlank
    private String title;
    private String content;
    private State state;
    private int progress;
    private String end_date;
    @Setter
    private Set<Tag> tags;
}
