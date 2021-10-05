package InTechs.InTechs.project.payload.response;

import InTechs.InTechs.project.value.IssuesCountInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class DashboardResponse {
    long userCount;
    IssuesCountInfo issuesCount;
}
