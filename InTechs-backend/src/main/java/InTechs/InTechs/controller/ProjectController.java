package InTechs.InTechs.controller;

import InTechs.InTechs.payload.ProjectCreateRequest;
import InTechs.InTechs.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @RequestParam ProjectCreateRequest project){
        // token.getUserId
        HttpHeaders headers = new HttpHeaders();
        headers.set("project number", projectService.createProject(project.getName(), token, project.getImage()));
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
}
