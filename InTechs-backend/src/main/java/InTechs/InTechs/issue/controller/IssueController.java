package InTechs.InTechs.issue.controller;

import InTechs.InTechs.issue.payload.IssueCreateRequest;
import InTechs.InTechs.issue.service.IssueService;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project")
public class IssueController {
    private final IssueService issueService;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/{projectId}/issue")
    public void issueCreate(@RequestHeader("Authorization") String token,@PathVariable int projectId, @Valid @RequestBody IssueCreateRequest issueRequest){
        issueService.issueCreate(jwtTokenProvider.getEmail(token), projectId, issueRequest);
    }
    @DeleteMapping("/{projectId}/issue/{issueId}")
    public void issueDelete(@PathVariable int projectId, @PathVariable String issueId){
        issueService.issueDelete(projectId, issueId);
    }
}
