package InTechs.InTechs.issue.repository;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;


@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {
    @Query("{projectId:?0, userIds:{$in:?1}")
    List<Issue> findProjectId(int projectId, List<String> userId);
    List<Issue> findAllByProjectId(int projectId);
    Long countByStateAndProjectId(State state, int projectId);

    List<Issue> findByEndDate(String endDate);

    List<Issue> findByProjectIdOrUsersContainingOrStateInOrTagsContaining(int projectId, List<User> users, List<State> state, Set<Tag> tags);
}
