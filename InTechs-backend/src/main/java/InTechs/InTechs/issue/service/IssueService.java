package InTechs.InTechs.issue.service;

import InTechs.InTechs.exception.exceptions.IssueNotFoundException;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.payload.request.IssueCreateRequest;
import InTechs.InTechs.issue.payload.request.IssueFilterRequest;
import InTechs.InTechs.issue.payload.request.IssueUpdateRequest;
import InTechs.InTechs.issue.payload.response.IssueFilterResponse;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.project.payload.response.UserIssueResponse;
import InTechs.InTechs.project.repository.ProjectRepository;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void issueCreate(String writer, int projectId, IssueCreateRequest issueRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .state(issueRequest.getState())
                .progress(issueRequest.getProgress())
                .endDate(issueRequest.getEnd_date())
                .tags(issueRequest.getTags())
                .writer(writer)
                .users(getUserListFromUsersEmail(issueRequest.getUsersId()))
                .projectId(projectId)
                .build();

        issueRepository.save(issue);

        if (issueRequest.getTags() != null)
            issueRequest.getTags().forEach(tag -> project.getTags().add(tag));

        project.addIssue(issue);
        projectRepository.save(project);
    }

    public void issueDelete(int projectId, String issueId) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.getIssues().remove(issueRepository.findById(issueId).orElseThrow(IssueNotFoundException::new));
        projectRepository.save(project);
        issueRepository.deleteById(issueId);
    }

    public void issueUpdate(String issueId, IssueUpdateRequest request) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(IssueNotFoundException::new);
        if (request.getContent() != null) issue.setContent(request.getContent());
        if (request.getEndDate() != null) issue.setEndDate(request.getEndDate());
        if (request.getProgress() != 0) issue.setProgress(request.getProgress());
        if (request.getTitle() != null) issue.setTitle(request.getTitle());
        if (request.getTags() != null) tagChange(issue, request.getTags());
        if (request.getState() != null) issue.setState(request.getState());
        issueRepository.save(issue);
    }

    private void tagChange(Issue issue, Set<Tag> tags) {
        Project project = projectRepository.findById(issue.getProjectId()).orElseThrow(ProjectNotFoundException::new);
        project.getTags().removeAll(tags);
        project.addTags(tags); // lambda
        projectRepository.save(project);
        issue.setTags(tags);
    }

    public List<IssueFilterResponse> issueFiltering(int projectId, IssueFilterRequest request) {

        List<Issue> issues = issueRepository.findAllByProjectId(projectId)
                .stream()
                .filter((i) -> {
                    if (request.getTags() == null) return true;
                    return i.getTags().containsAll(request.getTags());
                })
                .filter((i) -> {
                    if (request.getUsersId() == null) return true;
                    return i.getUsers().containsAll(getUserListFromUsersEmail(request.getUsersId()));
                })
                .filter((i) -> {
                    if (request.getStates() == null) return true;
                    return request.getStates().contains(i.getState());
                })
                .collect(Collectors.toList());

        List<IssueFilterResponse> filterIssues = new ArrayList<>();
        issues.forEach(
                (i) -> filterIssues.add(
                        IssueFilterResponse.builder()
                                .id(i.getId().toString())
                                .writer(i.getWriter())
                                .title(i.getTitle())
                                .content(i.getContent())
                                .state(i.getState())
                                .progress(i.getProgress())
                                .endDate(i.getEndDate())
                                .projectId(i.getProjectId())
                                .users(i.getUsers().stream().map(user ->
                                        UserIssueResponse.builder()
                                                .name(user.getName())
                                                .email(user.getEmail())
                                                .build())
                                        .collect(Collectors.toList()))
                                .tags(i.getTags())
                                .build()
                )
        );
        return filterIssues;
    }

    private List<User> getUserListFromUsersEmail(List<String> usersEmail) {
        if (usersEmail != null)
            return IteratorUtils.toList(userRepository.findAllById(usersEmail).iterator());
        return null;
    }
}
