package InTechs.InTechs.issue.payload.response;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.payload.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
public class IssueResponse {
    private final String id;
    private final String writer;
    private final String title;
    private final String content;
    private final State state;
    private final int progress;
    private final String endDate;
    private final List<UserResponse> users;
    private final List<IssueCommentResponse> comments;
    private final Set<Tag> tags;
}
