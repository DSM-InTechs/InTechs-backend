package InTechs.InTechs.project;

import InTechs.InTechs.repository.project.CustomProjectRepository;
import InTechs.InTechs.repository.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void nameChangeMethod(){
        int number = 297102;
        String name = "변경된 이름";
        projectRepository.projectUpdate(number, name);
    }
}
