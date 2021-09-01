package InTechs.InTechs.service;

import InTechs.InTechs.payload.request.SignInRequest;
import InTechs.InTechs.payload.request.SignUpRequest;
import InTechs.InTechs.payload.response.TokenResponse;

public interface AuthService {

    void SignUp(SignUpRequest signUpRequest);

    TokenResponse SignIn(SignInRequest signInRequest);

}
