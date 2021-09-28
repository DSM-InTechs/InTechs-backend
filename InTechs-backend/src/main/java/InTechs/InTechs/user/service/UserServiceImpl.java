package InTechs.InTechs.user.service;

import InTechs.InTechs.security.JwtTokenProvider;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.payload.request.IsActiveRequest;
import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${image.upload.dir}")
    private String imageDir;

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private final AuthenticationFacade authenticationFacade;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .isActive(user.getIsActive())
                .build();
    }

    @Override
    public MyPageResponse getMyPage() {
        String fileName = findUser().getEmail();

        File file = new File(imageDir, fileName);

        return MyPageResponse.builder()
                .name(findUser().getName())
                .email(findUser().getEmail())
                .image(fileName)
                .build();
    }

    @Override
    public void updateUser(ProfileRequest profileRequest) {

        String name = profileRequest.getName();

        MultipartFile image = profileRequest.getImage();

        userRepository.save(findUser().updateName(name));

        userRepository.save(findUser().updateImage(image.toString()));

    }

    @Override
    public List<MyProjectListResponse> getMyProject() {
        return projectRepository.findByUsersContainsOrderByCreateAtDesc(findUser()).stream()
                .map(project -> MyProjectListResponse.builder()
                        .id(project.getNumber())
                        .name(project.getName())
                        .createdAt(project.getCreateAt())
                        .image(project.getImage().getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateActive(IsActiveRequest isActiveRequest) {
        boolean isActive = isActiveRequest.getIsActive();

        userRepository.save(findUser().updateActive(isActive));
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

}
