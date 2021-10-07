package InTechs.InTechs.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IsActiveResponse {

    private final Boolean isActive;

}
