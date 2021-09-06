package InTechs.InTechs.issue.service;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.payload.IssueCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    public void issueCreate(int projectId, IssueCreateRequest issueRequest){

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .content(issueRequest.getContent())
                .state(issueRequest.getState())
                .progress(issueRequest.getProgress())
                .end_date(issueRequest.getEnd_date())
                .build();
    }
}
