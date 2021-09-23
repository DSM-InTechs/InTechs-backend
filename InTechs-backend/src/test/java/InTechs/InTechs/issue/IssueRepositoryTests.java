package InTechs.InTechs.issue;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class IssueRepositoryTests {
    @Autowired
    IssueRepository issueRepository;

    @Test
    public void issueUpdate(){
        Issue issue = issueRepository.findById("6138a5164b1d0d763fa4fcc4").orElseThrow();
        issue.setContent("그러지마");
        issue.setState(State.IN_PROGRESS);
        issueRepository.save(issue);
    }
}
