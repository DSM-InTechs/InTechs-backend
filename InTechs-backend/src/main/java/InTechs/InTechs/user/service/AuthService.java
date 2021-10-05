package InTechs.InTechs.user.service;

import InTechs.InTechs.user.payload.request.SignInRequest;
import InTechs.InTechs.user.payload.request.SignUpRequest;
import InTechs.InTechs.user.payload.response.TokenResponse;

public interface AuthService {

    void SignUp(SignUpRequest signUpRequest);

    TokenResponse SignIn(SignInRequest signInRequest);

    TokenResponse refreshToken(String token);

}
