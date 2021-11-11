package InTechs.InTechs.calendar.repository;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.value.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class CustomCalendarRepositoryImpl implements CustomCalendarRepository {

    private final MongoTemplate mongoTemplate;

    public List<Issue> findProjectAndEndDate(int projectId, String date) {
        return mongoTemplate.find(query(where("projectId").is(projectId)
                .and("endDate").regex(date)),
                Issue.class);
    }

    @Override
    public List<Issue> findByProjectId(int projectId, String user, String state, String tags) {
        return mongoTemplate.find(query(where("projectId").is(projectId)
                        .and("tag").is(tags)
                        .orOperator(where("writer").is(user),
                                (where("state")).is(state))),
                Issue.class);
    }

    public List<Issue> findByProjectIdAndTag(int projectId, String[] writer, String[] state, Set<Tag> tags) {
        return mongoTemplate.find(query(where("projectId").is(projectId)
                .and("tag").is(tags)
                .orOperator(where("writer").is(writer),
                        (where("state")).is(state))),
                Issue.class);
    }
}
