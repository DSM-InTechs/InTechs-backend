package InTechs.InTechs.issue;

import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.service.IssueService;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class IssueServiceTests {
    @Autowired
    IssueService issueService;
    @Test
    public void issueCreateTest(){
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.builder().tag("태그").build());
        tags.add(Tag.builder().tag("방가방가").build());
        IssueCreateRequest req = IssueCreateRequest.builder()
                .content("새로운이슈")
                .end_date("2020-02-03")
                .progress(88)
                .state(State.DONE)
                .title("제목")
                .tags(tags)
                .build();
        issueService.issueCreate("whddms@dsm.hs.kr",879487,req);
    }
}
