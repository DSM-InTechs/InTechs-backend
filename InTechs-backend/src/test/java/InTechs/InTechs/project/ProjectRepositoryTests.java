package InTechs.InTechs.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.repository.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    public void nameChangeTest(){
        int number = 297102;
        String name = "변경된 이름";
        projectRepository.projectUpdate(number, name);
    }
    @Test
    public void imageChangeTest(){
        int number = 297102;
        Image image = Image.builder()
                .imageUrl("어쨌든 url임")
                .oriName("어쨌든.jpg")
                .build();
        projectRepository.projectUpdate(number, image);
    }
    @Test
    public void getProjectTest(){
        int number = 297102;
        projectRepository.findById(number);
    }
}
