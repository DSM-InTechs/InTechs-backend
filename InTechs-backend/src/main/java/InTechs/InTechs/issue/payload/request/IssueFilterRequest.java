package InTechs.InTechs.issue.payload.request;

import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class IssueFilterRequest {
    Set<Tag> tags;
    @JsonProperty("users")
    List<String> userIds;
    List<State> states;
}
