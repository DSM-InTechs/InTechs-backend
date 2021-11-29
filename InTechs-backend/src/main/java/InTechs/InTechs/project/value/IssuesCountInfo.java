package InTechs.InTechs.project.value;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssuesCountInfo {
    int forMe;
    int resolved;
    int unresolved;
    int forMeAndUnresolved;
}
