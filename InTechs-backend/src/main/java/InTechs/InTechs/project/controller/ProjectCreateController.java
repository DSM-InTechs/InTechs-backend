package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.request.ProjectCreateRequest;
import InTechs.InTechs.project.service.ProjectCreateService;
import InTechs.InTechs.security.JwtTokenProvider;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectCreateController {
    private final ProjectCreateService projectCreateService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public ResponseEntity<String> projectCreate(@ModelAttribute ProjectCreateRequest project){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Project-Number", projectCreateService.createProject(project.getName(), authenticationFacade.getUserEmail(), project.getImage()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
