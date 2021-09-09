package InTechs.InTechs.mongoDB;

import InTechs.InTechs.user.entity.Project;
import InTechs.InTechs.user.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

// 연동 테스트
@SpringBootTest
public class MongoDBTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void insert(){
        Project project = Project.builder()
                .number(123456)
                .name("HI")
                .users(new ArrayList<>())
                .build();

        projectRepository.save(project);
    }

    @Test
    public void insertFromTemplate(){
        Project project = Project.builder()
                .number(654321)
                .name("HI")
                .users(new ArrayList<>())
                .build();

        mongoTemplate.insert(project);
    }
}
