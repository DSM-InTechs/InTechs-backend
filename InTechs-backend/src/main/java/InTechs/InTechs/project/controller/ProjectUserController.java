package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.service.ProjectUserService;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/project/{projectId}/user")
@RequiredArgsConstructor
public class ProjectUserController {
    private final ProjectUserService projectUserService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping
    public void projectJoin(@PathVariable int projectId){
        projectUserService.projectJoin(projectId, authenticationFacade.getUserEmail());
    }

    @DeleteMapping
    public void projectExit(@PathVariable int projectId){
        projectUserService.projectExit(projectId, authenticationFacade.getUserEmail());
    }
}
