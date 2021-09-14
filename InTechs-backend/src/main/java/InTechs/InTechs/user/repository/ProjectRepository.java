package InTechs.InTechs.user.repository;

import InTechs.InTechs.user.entity.Project;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, Integer> {
    Optional<Project> findByUsersContainsOrderByCreateAtDesc(User user);
}
