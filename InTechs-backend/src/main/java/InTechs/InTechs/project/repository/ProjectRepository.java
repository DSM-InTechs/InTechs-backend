package InTechs.InTechs.project.repository;

import InTechs.InTechs.project.entity.Project;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CustomProjectRepository, MongoRepository<Project, Integer> {
    List<Project> findByUsersContainsOrderByCreateAtDesc(User user);
}