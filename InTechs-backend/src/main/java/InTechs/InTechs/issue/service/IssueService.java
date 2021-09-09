package InTechs.InTechs.issue.service;

import InTechs.InTechs.exception.exceptions.IssueNotFoundException;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    public void issueCreate(String writer, int projectId, IssueCreateRequest issueRequest){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .state(issueRequest.getState())
                .progress(issueRequest.getProgress())
                .end_date(issueRequest.getEnd_date())
                .tags(issueRequest.getTags())
                .writer(writer)
                .projectId(projectId)
                .build();

        issueRepository.save(issue);

        issueRequest.getTags().forEach(tag -> project.getTags().add(tag));

        project.addIssue(issue);
        projectRepository.save(project);
    }

    public void issueDelete(int projectId, String issueId){
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.getIssues().remove(issueRepository.findById(issueId).orElseThrow(IssueNotFoundException::new));
        projectRepository.save(project);
        issueRepository.deleteById(issueId);
    }
}
