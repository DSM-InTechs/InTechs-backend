package InTechs.InTechs.issue.controller;

import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project")
public class IssueController {
    private final IssueService issueService;
    @PostMapping("/{projectId}/issue")
    public void issueCreate(@PathVariable int projectId, @RequestBody IssueCreateRequest issueRequest){
        issueService.issueCreate(projectId, issueRequest);
    }
}
