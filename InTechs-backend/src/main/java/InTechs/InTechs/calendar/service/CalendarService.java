package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.response.CalendarFilterResponse;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;

import java.util.List;

public interface CalendarService {

    List<CalendarResponse> getCalendar(String issueId);

    //List<CalendarFilterResponse> getFilterCalendar();

}
