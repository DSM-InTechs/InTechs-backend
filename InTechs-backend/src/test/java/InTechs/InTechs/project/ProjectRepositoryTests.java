package InTechs.InTechs.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.entity.Project;
import InTechs.InTechs.entity.User;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.repository.project.ProjectRepository;
import InTechs.InTechs.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProjectRepositoryTests {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

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

    @Test
    public void test(){
        Project project = projectRepository.findById(297102).orElseThrow(ProjectNotFoundException::new);
        System.out.println(project.getUsers().get(0).getEmail());
    }

    @Test
    public void test02(){
        List<User> users = new ArrayList<>();
        users.add(userRepository.findById("whddms@dsm.hs.kr").get());
        users.add(userRepository.findById("ddd@com").get());
        Project project = Project.builder()
                .number(879465)
                .name("proName")
                .image(Image.builder().build())
                .users(users).build();

        projectRepository.save(project);
    }

    @Test
    public void userUpdateTest(){
        Project project = projectRepository.findById(879465).orElseThrow(ProjectNotFoundException::new);
        project.addUser(userRepository.findById("ddd03@com").get());
        projectRepository.save(project);
    }
}
