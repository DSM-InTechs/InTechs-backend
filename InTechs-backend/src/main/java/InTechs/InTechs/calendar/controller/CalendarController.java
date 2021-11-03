package InTechs.InTechs.calendar.controller;

import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.calendar.service.CalendarService;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    @GetMapping ("/{projectId}/calendar/filter")
    public List<CalendarResponse> getFilterCalendar(@PathVariable int projectId,
                                                    @RequestParam(required = false) String[] email,
                                                    @RequestParam(required = false) String[] states,
                                                    @RequestParam(required = false) String[] tags) {

        return calendarService.getFilterCalendar(projectId, email, states, tags);
    }

    @GetMapping("/{projectId}/calendar/{deadline}")
    public List<CalendarResponse> getdeadline(@PathVariable int projectId,
                                              @PathVariable String deadline) {
        return calendarService.getDeadLine(projectId, deadline);
    }

}
