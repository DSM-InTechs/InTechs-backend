package InTechs.InTechs.calendar.repository;

import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

public interface CustomCalendarRepository {

    List<Issue> findProjectAndEndDate(int projectId, String date);

    List<Issue> findByProjectIdAndTag(int projectId, @Nullable String writer, @Nullable State state, @Nullable Set<Tag> tags);
}
