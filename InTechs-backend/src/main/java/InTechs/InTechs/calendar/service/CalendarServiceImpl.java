package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.request.FilterRequest;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.calendar.repository.CustomCalendarRepository;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CustomCalendarRepository customCalendarRepository;

    @Override
    public List<CalendarResponse> getCalendar(int projectId, int year, int month) {
        String monthString = String.valueOf(month);
        if (month < 10) {
            monthString = "0" + monthString;
        }

        List<Issue> issues = customCalendarRepository.findProjectAndEndDate(projectId, year + "-" + monthString);

        return buildCalendar(issues);
    }

    @Override
    public List<CalendarResponse> getFilterCalendar(int projectId, FilterRequest filterRequest) {

        Set<Tag> tags = filterRequest.getTags();
        State state = filterRequest.getState();
        String name = filterRequest.getName();

        List<Issue> issues = customCalendarRepository.findByProjectIdAndTag(projectId, name, state, tags);

        return buildCalendar(issues);
    }

    private List<CalendarResponse> buildCalendar(List<Issue> issues) {
        return issues.stream()
                .map(issue -> CalendarResponse.builder()
                        .id(issue.getId().toString())
                        .title(issue.getTitle())
                        .writer(issue.getWriter())
                        .state(issue.getState())
                        .progress(issue.getProgress())
                        .content(issue.getContent())
                        .endDate(issue.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }
}
