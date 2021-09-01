package InTechs.InTechs.controller;

import InTechs.InTechs.payload.ProjectCreateRequest;
import InTechs.InTechs.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @ModelAttribute ProjectCreateRequest project){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Project-Number", projectService.createProject(project.getName(), jwtTokenProvider.getEmail(token), project.getImage()));
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
}
