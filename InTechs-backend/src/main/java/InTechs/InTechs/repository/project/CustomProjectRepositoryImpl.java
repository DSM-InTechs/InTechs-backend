package InTechs.InTechs.repository.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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
}
