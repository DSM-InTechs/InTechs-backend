package InTechs.InTechs.project.service;

import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectUserService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    public void projectJoin(int projectId, String email){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.addUser(findUserFromEmail(email));
        projectRepository.save(project);
    }

    private User findUserFromEmail(String email){
        return userRepository.findById(email).orElseThrow(UserNotFoundException::new);
    }

    public void projectExit(int projectId, String email){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.removeUser(findUserFromEmail(email));
        projectRepository.save(project);
    }
}
