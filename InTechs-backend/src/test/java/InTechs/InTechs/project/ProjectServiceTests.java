package InTechs.InTechs.project;

import InTechs.InTechs.service.project.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTests {
    @Autowired
    ProjectService projectService;

    @Test
    public void projectJoinTest(){
        projectService.projectJoin(297102, "ddd@com");
    }
}
