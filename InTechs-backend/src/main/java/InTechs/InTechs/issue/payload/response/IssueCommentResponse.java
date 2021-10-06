package InTechs.InTechs.issue.payload.response;

import InTechs.InTechs.project.payload.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class IssueCommentResponse {
    private final String id;
    private final UserResponse user;
    private final String content;
    private final LocalDateTime createAt;
}
