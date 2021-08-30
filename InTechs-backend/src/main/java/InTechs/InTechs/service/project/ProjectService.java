package InTechs.InTechs.service.project;

import InTechs.InTechs.entity.Project;
import InTechs.InTechs.entity.User;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.payload.ProjectCreateRequest;
import InTechs.InTechs.repository.user.UserRepository;
import InTechs.InTechs.repository.project.ProjectRepository;
import InTechs.InTechs.service.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProjectService{

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;

    public void createProject(ProjectCreateRequest proReq, String userId, MultipartFile file) {
        int number = createProjectNumber();
        while (projectRepository.existsById(number)){
            number = createProjectNumber();
        }

        // String image = proReq.getImage();
        // 이미지 리사이즈 후 두 개 이미지 저장
        String fileUrl = fileUploadService.uploadImage(file);

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<User> users = new ArrayList<>();
        users.add(user);

        Project project = Project.builder()
                .number(number)
                .name(proReq.getName())
                .users(users).build();

        projectRepository.save(project);

        // return fileUrl
    }

    private int createProjectNumber(){
        final int len = 6;

        Random rand = new Random();

        StringBuilder id= new StringBuilder();

        for(int i = 0; i< len; i++){
            String ran = Integer.toString(rand.nextInt(10));
            id.append(ran);
        }

        return Integer.parseInt(id.toString());
    }
}
