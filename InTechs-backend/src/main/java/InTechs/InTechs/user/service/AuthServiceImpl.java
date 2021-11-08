package InTechs.InTechs.user.service;

import InTechs.InTechs.exception.exceptions.InvalidTokenException;
import InTechs.InTechs.exception.exceptions.UserAlreadyException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.entity.RefreshToken;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.payload.request.SignInRequest;
import InTechs.InTechs.user.payload.request.SignUpRequest;
import InTechs.InTechs.user.payload.response.TokenResponse;
import InTechs.InTechs.user.repository.RefreshTokenRepository;
import InTechs.InTechs.user.repository.UserRepository;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshTokenTime;

    @Value("${auth.jwt.prefix}")
    private String tokenType;

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
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .isActive(false)
                        .build()
        );
    }

    @Override
    public TokenResponse SignIn(SignInRequest signInRequest) {
        userRepository.findByEmail(signInRequest.getEmail())
                .filter(user -> passwordEncoder.matches(signInRequest.getPassword(), user.getPassword()))
                .orElseThrow(UserNotFoundException::new);

        RefreshToken refreshToken = refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(signInRequest.getEmail())
                        .refreshToken(jwtTokenProvider.generateRefreshToken(signInRequest.getEmail()))
                        .refreshTokenTime(refreshTokenTime)
                        .build());

        return TokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(signInRequest.getEmail()))
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    @Override
    public TokenResponse refreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
                .map(refreshToken -> {
                    String generatedAccessToken = jwtTokenProvider.generateRefreshToken(refreshToken.getEmail());
                    return refreshToken.update(generatedAccessToken, refreshTokenTime);
                })
                .map(refreshTokenRepository::save)
                .map(refreshToken -> {
                    String generatedAccessToken =jwtTokenProvider.generateAccessToken(refreshToken.getEmail());
                    return new TokenResponse(generatedAccessToken, refreshToken.getRefreshToken(), tokenType);
                })
                .orElseThrow(InvalidTokenException::new);
    }

}
