package InTechs.InTechs.issue.repository;

import InTechs.InTechs.issue.entity.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IssueRepository extends MongoRepository<Issue, String> {
    @Query("{projectId:?0, userIds:{$in:?1}")
    List<Issue> findProjectId(int projectId, List<String> userId);
    List<Issue> findAllByProjectId(int projectId);
}
