package InTechs.InTechs.issue.repository;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {
    List<Issue> findByProjectIdAndEndDateStartingWith(int projectId, String date);

    @Query("{$and:[{projectId: ?1}, {$and:[{$or: [{$ne: null}, {writer: {$regex: ?2}}]}, {$or: [{$ne: null}, {state: {$regex: ?3}}]}, {$or: [{$ne: null}, {tag: {$regex: ?2}}]}]}]}")
    List<Issue> findByProjectIdAndTag(int projectId, String writer, State state, Set<Tag> tags);
}
