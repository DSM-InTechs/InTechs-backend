package InTechs.InTechs.user.service;

import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.user.entity.Project;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.exception.exceptions.UserAlreadyException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.IsActiveResponse;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.user.repository.ProjectRepository;
import InTechs.InTechs.user.repository.UserRepository;
import InTechs.InTechs.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${image.upload.dir}")
    private String imageDir;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .isActive(user.isActive())
                .build();
    }

    @Override
    public MyPageResponse getMyPage(String token) {
        User user = userRepository.findByEmail(jwtTokenProvider.getEmail(token))
                .orElseThrow(UserAlreadyException::new);

        String fileName = user.getEmail();

        File file = new File(imageDir, fileName);

        return MyPageResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .image(fileName)
                .build();
    }

    @Override
    public void updateUser(ProfileRequest profileRequest, String token) {
        User user = userRepository.findByEmail(token)
                .orElseThrow(UserNotFoundException::new);

        String name = profileRequest.getName();

        MultipartFile image = profileRequest.getImage();

        userRepository.save(user.setName(name));

        userRepository.save(user.setImage(image.toString()));
    }

    @Override
    public List<MyProjectListResponse> getMyProject(String token) {

        return null;
    }

    @Override
    public IsActiveResponse getActive(Boolean isActive) {
        return null;
    }

}
