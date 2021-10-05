package InTechs.InTechs.issue;

import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static InTechs.InTechs.issue.value.State.DONE;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class IssueRepositoryTests {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    UserRepository userRepository;

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

    @Test
    public void issueStateCountTest(){
        long c = issueRepository.countByStateAndProjectId(DONE, 879487);
        assertThat(c).isEqualTo(2);
    }

    @Test
    public void myIssueCountTest(){
        int projectId = 879487;
        String userId = "whddms@dsm.hs.kr";
        long myIssueCount = issueRepository.findAllByProjectId(projectId).stream().filter(
                (a)->a.getUsers().contains(
                        userRepository.findById(userId).orElseThrow(UserNotFoundException::new)
                )).count();
        assertThat(myIssueCount).isEqualTo(2);
    }
}
