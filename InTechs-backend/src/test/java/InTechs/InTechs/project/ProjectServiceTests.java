package InTechs.InTechs.project;

import InTechs.InTechs.payload.ProjectCreateRequest;
import InTechs.InTechs.service.project.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProjectServiceTests {
    @Autowired
    private ProjectService projectService;

    @Test
    public void projectCrate(){
        ProjectCreateRequest projectCreateRequest = ProjectCreateRequest.builder()
                .name("zz2")
                .image("dddd")
                .build();
        String email = "whddms@dsm.hs.kr";
        projectService.createProject(projectCreateRequest, email);
    }
}
