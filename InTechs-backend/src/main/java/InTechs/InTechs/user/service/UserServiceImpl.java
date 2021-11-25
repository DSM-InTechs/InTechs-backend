package InTechs.InTechs.user.service;

import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.payload.request.IsActiveRequest;
import InTechs.InTechs.user.payload.request.ProfileRequest;
import InTechs.InTechs.user.payload.response.MyPageResponse;
import InTechs.InTechs.user.payload.response.MyProjectListResponse;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private final FileUploader fileUploader;

    private final AuthenticationFacade authenticationFacade;

    @Override
    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return ProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getFileUrl())
                .isActive(user.getIsActive())
                .build();
    }

    @Override
    public MyPageResponse getMyPage() {

        return MyPageResponse.builder()
                .name(findUser().getName())
                .email(findUser().getEmail())
                .image(findUser().getFileUrl())
                .build();
    }

    @SneakyThrows
    @Override
    public void updateUser(ProfileRequest profileRequest) {

        String name = profileRequest.getName();
        MultipartFile file = profileRequest.getImage();
        String fileName = UUID.randomUUID().toString();

        fileUploader.uploadFile(file, fileName);

        String fileUrl = fileUploader.getObjectUrl(fileName);

        userRepository.save(findUser().updateName(name));
        userRepository.save(findUser().updateFileUrl(fileUrl));
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
        Boolean isActive = isActiveRequest.getIsActive();

        userRepository.save(findUser().updateActive(isActive));
    }

    @Override
    public void deleteUser() {
        userRepository.delete(findUser());
    }

    private User findUser() {
        return userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }
}
