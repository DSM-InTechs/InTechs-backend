package InTechs.InTechs.calendar.repository;

import InTechs.InTechs.issue.entity.Issue;

import java.util.List;

public interface CustomCalendarRepository {

    List<Issue> findProjectAndEndDate(int projectId, String date);

    List<Issue> findByProjectId(int projectId, String user, String state, String tags);

}
