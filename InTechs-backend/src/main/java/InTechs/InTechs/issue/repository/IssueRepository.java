package InTechs.InTechs.issue.repository;

import InTechs.InTechs.issue.entity.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssueRepository extends MongoRepository<Issue, String> {
}
