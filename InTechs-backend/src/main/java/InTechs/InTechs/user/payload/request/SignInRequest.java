package InTechs.InTechs.user.payload.request;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class SignInRequest {

    private final String email;
    private final String password;
    private final String targetToken;

}
