package InTechs.InTechs.project.service;

import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.payload.response.UserIssueResponse;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.project.payload.response.ProjectUserResponse;
import InTechs.InTechs.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectUserResponse> projectUserList(int projectId){
        List<User> users = projectRepository.findById(projectId).map(Project::getUsers).orElseThrow(ProjectNotFoundException::new);
        List<ProjectUserResponse> userListResponse = new ArrayList<>();
        for(User user :users){
            ProjectUserResponse userResponse =
                    ProjectUserResponse.builder()
                            .email(user.getEmail())
                            .name(user.getName())
                            .imageUri(user.getImage())
                            .isActive(user.getIsActive()).build();
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
}
