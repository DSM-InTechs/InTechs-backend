package InTechs.InTechs.project.repository;

import InTechs.InTechs.project.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends CustomProjectRepository, MongoRepository<Project, Integer> {
}
