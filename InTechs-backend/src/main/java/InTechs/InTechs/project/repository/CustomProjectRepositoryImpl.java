package InTechs.InTechs.project.repository;

import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@RequiredArgsConstructor
public class CustomProjectRepositoryImpl implements CustomProjectRepository{
    private final MongoTemplate mongoTemplate;

    public void projectUpdate(int projectId, String name){
        String column = "name";
        Query query = new Query(Criteria.where("_id").is(projectId));

        Update update= Update.update(column, name);

        mongoTemplate.updateFirst(query,update, Project.class);
    }

    public void projectUpdate(int projectId, Image image){
        String column = "image";
        Query query = new Query(Criteria.where("_id").is(projectId));

        Update update= Update.update(column, image);

        mongoTemplate.updateFirst(query,update, Project.class);
    }

    public void addProjectUser(int projectId, List<User> users){
        String column = "users";
        Query query = new Query(Criteria.where("_id").is(projectId));
        Update update = Update.update(column, users);
        mongoTemplate.updateFirst(query, update, Project.class);
    }
}
