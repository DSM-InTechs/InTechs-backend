package InTechs.InTechs.user.payload.request;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class TokenRequest {

    private final String refreshToken;

}
