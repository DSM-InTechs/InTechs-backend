package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.request.ProjectInfoChangeRequest;
import InTechs.InTechs.project.payload.response.DashboardResponse;

import InTechs.InTechs.project.service.*;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectDashboardService dashboardService;
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;

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
        projectUserService.projectJoin(projectId, jwtTokenProvider.getEmail(token));
    }

    @DeleteMapping("/{projectId}/user")
    public void projectExit(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        projectUserService.projectExit(projectId, jwtTokenProvider.getEmail(token));
    }

    @GetMapping("/{projectId}/dashboard")
    public DashboardResponse projectDashboard(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        return dashboardService.projectDashboard(projectId, jwtTokenProvider.getEmail(token));
    }
}
