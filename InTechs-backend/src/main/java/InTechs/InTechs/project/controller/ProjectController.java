package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.request.ProjectInfoChangeRequest;
import InTechs.InTechs.project.payload.response.DashboardResponse;

import InTechs.InTechs.project.payload.response.ProjectInfoResponse;
import InTechs.InTechs.project.service.*;
import InTechs.InTechs.security.JwtTokenProvider;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
@RequiredArgsConstructor
@RequestMapping("/project/{projectId}")
public class ProjectController {
    private final AuthenticationFacade authenticationFacade;
    private final ProjectDashboardService dashboardService;
    private final ProjectService projectService;

    @PatchMapping
    public void projectInfoChange(@PathVariable int projectId, @ModelAttribute ProjectInfoChangeRequest projectChangeInfo){
        projectService.projectInfoChange(projectId,projectChangeInfo.getName(), projectChangeInfo.getImage());
    }

    @DeleteMapping
    public void projectDelete(@PathVariable int projectId){
        projectService.projectDelete(projectId);
    }

    @GetMapping("/dashboard")
    public DashboardResponse projectDashboard(@RequestHeader("Authorization") String token, @PathVariable int projectId){
        return dashboardService.projectDashboard(projectId, authenticationFacade.getUserEmail());
    }

    @GetMapping
    public ProjectInfoResponse projectInfo(@PathVariable int projectId){
        return projectService.projectInfo(projectId);
    }
}
