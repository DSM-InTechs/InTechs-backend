package InTechs.InTechs.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponse {

    private final String name;
    private final String email;
    private final String image;
    private final Boolean isActive;

}
