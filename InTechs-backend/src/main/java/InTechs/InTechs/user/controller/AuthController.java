package InTechs.InTechs.user.controller;

import InTechs.InTechs.user.payload.request.SignInRequest;
import InTechs.InTechs.user.payload.request.SignUpRequest;
import InTechs.InTechs.user.payload.response.TokenResponse;
import InTechs.InTechs.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.SignUp(signUpRequest);
    }

    @PostMapping("/auth")
    public TokenResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.SignIn(signInRequest);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestHeader("Refresh_Token") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
