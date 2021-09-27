package InTechs.InTechs.calendar.controller;

import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/{projectId}/calendar")
    public List<CalendarResponse> getCalendar(String issueId) {
        return calendarService.getCalendar(issueId);
    }



}
