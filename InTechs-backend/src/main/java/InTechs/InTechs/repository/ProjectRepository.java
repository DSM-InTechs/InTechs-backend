package InTechs.InTechs.repository;

import InTechs.InTechs.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, Integer> {
}
