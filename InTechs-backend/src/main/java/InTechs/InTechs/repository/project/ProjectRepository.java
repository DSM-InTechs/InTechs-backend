package InTechs.InTechs.repository.project;

import InTechs.InTechs.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends CustomProjectRepository, MongoRepository<Project, Integer> {
}
