package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.request.FilterRequest;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;

import java.util.List;
import java.util.Set;

public interface CalendarService {

    List<CalendarResponse> getCalendar(int projectId, int year, int month);

    List<CalendarResponse> getFilterCalendar(int projectId, FilterRequest filterRequest);

}
