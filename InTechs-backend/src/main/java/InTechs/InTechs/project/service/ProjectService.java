package InTechs.InTechs.project.service;

import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.payload.response.DashboardResponse;
import InTechs.InTechs.project.payload.response.UserIssueResponse;
import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.value.IssuesCountInfo;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.project.payload.response.ProjectUserResponse;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.user.repository.UserRepository;
import InTechs.InTechs.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static InTechs.InTechs.issue.value.State.*;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final FileService fileUploadService;
    private final UserRepository userRepository;
    private final IssueRepository issueRepository; // dashboard

    public void projectInfoChange(int projectId, String name,MultipartFile image){
        if(name!=null && !name.equals(" ")) changeProjectName(projectId,name);
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

    public List<ProjectUserResponse> projectUserList(int projectId){
        List<User> users = projectRepository.findById(projectId).map(Project::getUsers).orElseThrow(ProjectNotFoundException::new);
        List<ProjectUserResponse> userListResponse = new ArrayList<>();
        for(User user :users){
            ProjectUserResponse userResponse =
                    ProjectUserResponse.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .imageUri(user.getImage())
                            .isActive(user.isActive()).build();
            userListResponse.add(userResponse);
        }
        return userListResponse;
    }
    public Set<Tag> tagList(int projectId){
        return projectRepository.findById(projectId).map(Project::getTags).orElseGet(HashSet::new);
    }

    public Set<UserIssueResponse> userTagList(int projectId){
        List<User> users = projectRepository.findById(projectId).map(Project::getUsers).orElseThrow(ProjectNotFoundException::new);
        Set<UserIssueResponse> userTagList = new HashSet<>();
        for(User user: users){
            UserIssueResponse userTagResponse =
                    UserIssueResponse.builder()
                                    .email(user.getEmail())
                                    .name(user.getName()).build();
            userTagList.add(userTagResponse);
        }
        return userTagList;

    }

    public DashboardResponse projectDashboard(int projectId, String userId){
        long userCount = projectRepository.findById(projectId).map(Project::getName).stream().count(); // 프로젝트에 속한 유저 수
        long unresolved = issueRepository.countByStateAndProjectId(IN_PROGRESS, projectId)+issueRepository.countByStateAndProjectId(READY, projectId);
        long resolved = issueRepository.countByStateAndProjectId(DONE, projectId);
        ;
        long myIssueCount = issueRepository.findAllByProjectId(projectId).stream().filter(
                (a)->a.getUsers().contains(
                        userRepository.findById(userId).orElseThrow(UserNotFoundException::new)
                )).count();

        IssuesCountInfo issuesCountInfo = IssuesCountInfo.builder()
                .forMe(myIssueCount)
                .resolved(resolved)
                .unresolved(unresolved)
                .foreMeAndUnresolved(myIssueCount+unresolved)
                .build();

        return DashboardResponse.builder()
                .issuesCount(issuesCountInfo)
                .userCount(userCount)
                .build();
    }
}
