package InTechs.InTechs.project.value;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssuesCountInfo {
    int resolved;
    int unresolved;
    long forMe;
    long forMeAndUnresolved;
}
