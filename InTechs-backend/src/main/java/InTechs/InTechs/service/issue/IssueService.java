package InTechs.InTechs.service.issue;

import InTechs.InTechs.entity.Issue;
import InTechs.InTechs.payload.request.issue.IssueCreateRequest;
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
