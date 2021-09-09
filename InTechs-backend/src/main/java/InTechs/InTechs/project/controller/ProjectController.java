package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.request.ProjectCreateRequest;
import InTechs.InTechs.project.payload.request.ProjectInfoChangeRequest;
import InTechs.InTechs.project.payload.response.ProjectUserResponse;
import InTechs.InTechs.security.JwtTokenProvider;
import InTechs.InTechs.project.service.ProjectCreateService;
import InTechs.InTechs.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectCreateService projectCreateService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @ModelAttribute ProjectCreateRequest project){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Project-Number", projectCreateService.createProject(project.getName(), jwtTokenProvider.getEmail(token), project.getImage()));
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public void projectInfoChange(@PathVariable int projectId, @ModelAttribute ProjectInfoChangeRequest projectChangeInfo){
        projectService.projectInfoChange(projectId,projectChangeInfo.getName(), projectChangeInfo.getImage());
    }

    @DeleteMapping("/{projectId}")
    public void projectDelete(@PathVariable int projectId){
        projectService.projectDelete(projectId);
    }

    @PostMapping("/{projectId}/user")
    public void projectJoin(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        projectService.projectJoin(projectId, jwtTokenProvider.getEmail(token));
    }

    @DeleteMapping("/{projectId}/user")
    public void projectExit(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        projectService.projectExit(projectId, jwtTokenProvider.getEmail(token));
    }

    @GetMapping("/{projectId}/user")
    public List<ProjectUserResponse> projectUserList(@PathVariable int projectId){
        return projectService.projectUserList(projectId);
    }

    @GetMapping("/{projectId}/tag/{tagNum}")
    public Set<?> tagList(@PathVariable int projectId, @PathVariable int tagNum){
        if(tagNum==1){
            return projectService.userTagList(projectId);
        } else{
            return projectService.tagList(projectId);
        }
    }
}
