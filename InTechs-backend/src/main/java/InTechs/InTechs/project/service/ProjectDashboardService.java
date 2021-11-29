package InTechs.InTechs.project.service;

import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.payload.response.DashboardResponse;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.project.value.IssuesCountInfo;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static InTechs.InTechs.issue.value.State.*;

@RequiredArgsConstructor
@Service
public class ProjectDashboardService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public DashboardResponse projectDashboard(int projectId, String userId){
        int userCount = projectRepository.findById(projectId).map(Project::getUsers).orElseThrow(ProjectNotFoundException::new).size();

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
                .forMeAndUnresolved(myIssueCount+unresolved)
                .build();

        return DashboardResponse.builder()
                .issuesCount(issuesCountInfo)
                .userCount(userCount)
                .build();
    }
}
