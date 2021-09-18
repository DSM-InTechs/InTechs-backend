package InTechs.InTechs.issue;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @Test
    public void issueFiltering(){
        int projectId = 879487;
        List<String> users = new ArrayList<>();
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.builder().tag("백엔드").build());
        users.add("whddms");
        issueRepository.findProjectId(projectId, users);
    }
}
