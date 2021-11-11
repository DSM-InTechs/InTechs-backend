package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.calendar.repository.CustomCalendarRepository;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.issue.value.State;
import InTechs.InTechs.issue.value.Tag;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CustomCalendarRepository customCalendarRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

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
    public List<CalendarResponse> getFilterCalendar(int projectId, String[] emails, String[] states, String[] tags) {
        
        List<User> users = Arrays.stream(Optional.ofNullable(emails).orElse(new String[0]))
                .map(this::getUser)
                .collect(Collectors.toList());

        List<State> state = Arrays.stream(Optional.ofNullable(states).orElse(new String[0]))
                .map(State::valueOf)
                .collect(Collectors.toList());

        Set<Tag> tag = Arrays.stream(Optional.ofNullable(tags).orElse(new String[0]))
                .map(Tag::valueOf)
                .collect(Collectors.toSet());

        List<Issue> issues = issueRepository.findByProjectIdOrUsersContainingOrStateInOrTagsContaining(projectId, users, state, tag);

        return buildCalendar(issues);
    }

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

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
