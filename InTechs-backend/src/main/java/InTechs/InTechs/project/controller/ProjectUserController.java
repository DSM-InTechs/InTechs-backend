package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.service.ProjectUserService;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/project/{projectId}/user")
@RequiredArgsConstructor
public class ProjectUserController {
    private final ProjectUserService projectUserService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public void projectJoin(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        projectUserService.projectJoin(projectId, jwtTokenProvider.getEmail(token));
    }

    @DeleteMapping
    public void projectExit(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        projectUserService.projectExit(projectId, jwtTokenProvider.getEmail(token));
    }
}
