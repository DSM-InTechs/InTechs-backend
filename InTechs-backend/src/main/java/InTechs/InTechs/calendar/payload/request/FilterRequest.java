package InTechs.InTechs.calendar.payload.request;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {

    private Set<Tag> tags;
    private State state;
    private String name;

}
