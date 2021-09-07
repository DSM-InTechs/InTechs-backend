package InTechs.InTechs.issue.service;

import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;

    public void issueCreate(String writer, int projectId, IssueCreateRequest issueRequest){
        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .state(issueRequest.getState())
                .progress(issueRequest.getProgress())
                .end_date(issueRequest.getEnd_date())
                .tags(issueRequest.getTags())
                .writer(writer)
                .build();

        issueRepository.save(issue);
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        // 태그 중복처리...해야함...
        issueRequest.getTags().forEach(tag -> project.getTags().add(tag));

        project.addIssue(issue);
        projectRepository.save(project);
    }
}
