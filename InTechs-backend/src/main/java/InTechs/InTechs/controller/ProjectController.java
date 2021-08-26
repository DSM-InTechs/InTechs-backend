package InTechs.InTechs.controller;

import InTechs.InTechs.payload.ProjectCreateRequest;
import InTechs.InTechs.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @RequestBody @Valid ProjectCreateRequest project){
        projectService.createProject(project, token); // token.getUserId
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
