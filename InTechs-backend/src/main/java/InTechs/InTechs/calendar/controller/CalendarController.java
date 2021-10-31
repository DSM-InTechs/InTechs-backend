package InTechs.InTechs.calendar.controller;

import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.calendar.service.CalendarService;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
                                                    @Nullable @RequestParam("tag") Set<Tag> tag,
                                                    @Nullable @RequestParam("state") State state,
                                                    @Nullable @RequestParam("name") String name) {
        return calendarService.getFilterCalendar(projectId, tag, state, name);
    }

    @GetMapping
    public void asd(@RequestParam("asd") String[] asd) {
        System.out.println(Arrays.toString(asd));
    }

}
