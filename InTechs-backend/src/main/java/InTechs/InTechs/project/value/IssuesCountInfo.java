package InTechs.InTechs.project.value;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class IssuesCountInfo {
    long forMe;
    long resolved;
    long unresolved;
    long forMeAndUnresolved;
}
