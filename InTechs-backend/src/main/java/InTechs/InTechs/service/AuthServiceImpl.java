package InTechs.InTechs.service;

import InTechs.InTechs.entity.User;
import InTechs.InTechs.exception.exceptions.UserAlreadyException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.payload.request.SignInRequest;
import InTechs.InTechs.payload.request.SignUpRequest;
import InTechs.InTechs.payload.response.TokenResponse;
import InTechs.InTechs.repository.UserRepository;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void SignUp(SignUpRequest signUpRequest) {
       userRepository.findByEmail(signUpRequest.getEmail())
               .ifPresent(user -> {
                   throw new UserAlreadyException();
               });

       userRepository.save(
               User.builder()
                       .name(signUpRequest.getName())
                       .email(signUpRequest.getEmail())
                       .password(signUpRequest.getPassword())
                       .build()
       );
    }

    @Override
    public TokenResponse SignIn(SignInRequest signInRequest) {

        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        return userRepository.findByEmail(signInRequest.getEmail())
                .filter(user -> passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
                .map(user -> tokenResponse(user.getEmail()))
                .orElseThrow(UserNotFoundException::new);
    }

    private TokenResponse tokenResponse(String email) {
        String accessToken = jwtTokenProvider.generateAccessToken(email);
        String refreshToken = jwtTokenProvider.generateRefreshToken(email);

        return new TokenResponse(accessToken, refreshToken);
    }

}
