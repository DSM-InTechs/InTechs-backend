package InTechs.InTechs.controller;

import InTechs.InTechs.entity.Issue;
import InTechs.InTechs.payload.request.issue.IssueCreateRequest;
import InTechs.InTechs.service.issue.IssueService;
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
