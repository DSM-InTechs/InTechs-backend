package InTechs.InTechs.calendar.payload.request;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.*;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class FilterRequest {

    private final Set<Tag> tags;
    private final State state;
    private final String name;

}
