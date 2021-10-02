package InTechs.InTechs.project.value;

import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
public class IssuesCountInfo {
    long forMe;
    long resolved;
    long unresolved;
    long foreMeAndUnresolved;
}
