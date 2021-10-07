package InTechs.InTechs.project.controller;

import InTechs.InTechs.project.payload.response.ProjectUserResponse;
import InTechs.InTechs.project.service.ProjectListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/project/{projectId}")
@RequiredArgsConstructor
@RestController
public class ProjectListController {
    private final ProjectListService projectListService;

    @GetMapping("/user")
    public List<ProjectUserResponse> projectUserList(@PathVariable int projectId){
        return projectListService.projectUserList(projectId);
    }

    @GetMapping("/tag/{tagNum}")
    public Set<?> tagList(@PathVariable int projectId, @PathVariable int tagNum){
        return tagNum==1 ? projectListService.userTagList(projectId) : projectListService.tagList(projectId);
    }
}
