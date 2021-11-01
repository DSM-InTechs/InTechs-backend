package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.request.FilterRequest;
import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.exception.exceptions.IssueNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.calendar.repository.CustomCalendarRepository;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CustomCalendarRepository customCalendarRepository;
    private final IssueRepository issueRepository;

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
    public List<CalendarResponse> getFilterCalendar(int projectId, Collection<List<User>> users, Collection<State> state, Collection<Set<Tag>> tags) {
        List<Issue> issues = issueRepository.findByProjectIdAndUsersInOrStateInOrTagsIn(projectId, users, state, tags);

        return buildCalendar(issues);
    }

    /*
    private List<CalendarResponse> geFilterCalendar(int projectId, FilterRequest filterRequest) {

        Set<Tag> tags = filterRequest.getTags();
        State state = filterRequest.getState();
        String name = filterRequest.getName();

        List<Issue> issues = customCalendarRepository.findByProjectIdAndTag(projectId, name, state, tags);

        return buildCalendar(issues);
    }*/

    @Override
    public List<CalendarResponse> getDeadLine(int projectId, String date) {
        List<Issue> issues = issueRepository.findByEndDate(date);

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
                        .tags(issue.getTags())
                        .build())
                .collect(Collectors.toList());
    }
}
