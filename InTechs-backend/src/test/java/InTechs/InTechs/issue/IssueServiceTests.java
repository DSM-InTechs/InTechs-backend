package InTechs.InTechs.issue;

import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.service.IssueService;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IssueServiceTests {
    @Autowired
    IssueService issueService;
    @Test
    public void issueCreateTest(){
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.builder().tag("안녕").build());
        IssueCreateRequest req = IssueCreateRequest.builder()
                .content("이슈내용")
                .end_date("2020-02-03")
                .progress(98)
                .state(State.DONE)
                .title("제목")
                .tags(tags)
                .build();
        issueService.issueCreate("whddms@dsm.hs.kr",879465,req);
    }
}
