package InTechs.InTechs.calendar.controller;

import InTechs.InTechs.calendar.payload.request.FilterRequest;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.calendar.service.CalendarService;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/{projectId}/calendar")
    public List<CalendarResponse> getCalendar(@PathVariable int projectId,
                                              @RequestParam("year") int year,
                                              @RequestParam("month") int month) {
        return calendarService.getCalendar(projectId, year, month);
    }

    @GetMapping("/{projectId}/calendar/filter")
    public List<CalendarResponse> getFilterCalendar(@PathVariable int projectId,
                                                    @RequestBody FilterRequest filterRequest) {
        return calendarService.getFilterCalendar(projectId, filterRequest);
    }

}
