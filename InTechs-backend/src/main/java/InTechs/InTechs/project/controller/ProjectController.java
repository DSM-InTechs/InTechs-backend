package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.request.ProjectCreateRequest;
import InTechs.InTechs.project.payload.request.ProjectInfoChangeRequest;
import InTechs.InTechs.project.payload.response.DashboardResponse;

import InTechs.InTechs.project.service.*;
import InTechs.InTechs.security.JwtTokenProvider;
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
    private final ProjectCreateService projectCreateService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProjectDashboardService dashboardService;
    private final ProjectUpdateDeleteService updateDeleteService;
    private final ProjectUserService projectUserService;

    @PostMapping
    public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @ModelAttribute ProjectCreateRequest project){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Project-Number", projectCreateService.createProject(project.getName(), jwtTokenProvider.getEmail(token), project.getImage()));
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @PatchMapping("/{projectId}")
    public void projectInfoChange(@PathVariable int projectId, @ModelAttribute ProjectInfoChangeRequest projectChangeInfo){
        updateDeleteService.projectInfoChange(projectId,projectChangeInfo.getName(), projectChangeInfo.getImage());
    }

    @DeleteMapping("/{projectId}")
    public void projectDelete(@PathVariable int projectId){
        updateDeleteService.projectDelete(projectId);
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
