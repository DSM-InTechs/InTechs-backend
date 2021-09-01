package InTechs.InTechs.controller;

import InTechs.InTechs.payload.request.SignUpRequest;
import InTechs.InTechs.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.SignUp(signUpRequest);
    }

}
