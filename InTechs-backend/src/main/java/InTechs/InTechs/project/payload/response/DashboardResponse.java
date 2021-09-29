package InTechs.InTechs.project.payload.response;

import InTechs.InTechs.project.value.IssuesCountInfo;
import lombok.Builder;

@Builder
public class DashboardResponse {
    long userNumber;
    IssuesCountInfo issuesCount;
    // 이번달캘린더
}
