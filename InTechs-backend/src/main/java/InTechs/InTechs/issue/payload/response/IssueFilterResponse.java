package InTechs.InTechs.issue.payload.response;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.payload.response.UserResponse;
import lombok.*;

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
    private String endDate;
    private int projectId;
    private List<UserResponse> users;
    private Set<Tag> tags;
    private List<IssueCommentResponse> comments;
}
