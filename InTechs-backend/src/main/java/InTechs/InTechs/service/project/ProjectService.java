package InTechs.InTechs.service.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.entity.Project;
import InTechs.InTechs.entity.User;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.repository.project.ProjectRepository;
import InTechs.InTechs.repository.user.UserRepository;
import InTechs.InTechs.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FileService fileUploadService;
    private final UserRepository userRepository;

    public void projectInfoChange(int projectId, String name,MultipartFile image){
        if(name!=null) changeProjectName(projectId,name);
        if(image!=null) changeProjectImage(projectId, image);
    }

    private void changeProjectImage(int projectId, MultipartFile image){
        String folder = "/project";
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        Image imageInfo = fileUploadService.imageResizeAndUpload(image, folder);
        fileUploadService.fileDelete(folder, project.getImage().getOriName()); // s3에 저장되어있는 파일 삭제
        projectRepository.projectUpdate(projectId, imageInfo);
    }

    private void changeProjectName(int projectId, String name){
        projectRepository.projectUpdate(projectId,name);
    }

    public  void projectDelete(int projectId){
        String folder = "/project";
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new); // query dsl로 ㅂ꾸기
        fileUploadService.fileDelete(folder, project.getImage().getOriName());
        projectRepository.delete(project);
    }

    // use userRepository
    public void projectJoin(int projectId, String email){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        List<User> users = project.getUsers();
        users.add(findUserFromEmail(email));
        projectRepository.addProjectUser(projectId, users);
    }
    private User findUserFromEmail(String email){
        return userRepository.findById(email).orElseThrow(UserNotFoundException::new);
    }
}
