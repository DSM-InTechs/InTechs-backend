package InTechs.InTechs.project.payload.response;

import InTechs.InTechs.project.value.IssuesCountInfo;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class DashboardResponse {
    long userCount;
    IssuesCountInfo issuesCount;
    // 이번달캘린더
}
