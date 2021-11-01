package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.request.FilterRequest;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CalendarService {

    List<CalendarResponse> getCalendar(int projectId, int year, int month);

    List<CalendarResponse> getFilterCalendar(int projectId, Collection<List<User>> users, Collection<State> state, Collection<Set<Tag>> tags);

    List<CalendarResponse> getDeadLine(int projectId, String date);

}
