package InTechs.InTechs.service.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.entity.Project;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.repository.project.ProjectRepository;
import InTechs.InTechs.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FileService fileUploadService;

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
}
