package InTechs.InTechs.calendar.service;

import InTechs.InTechs.calendar.payload.response.CalendarResponse;
import InTechs.InTechs.calendar.repository.CalendarRepository;
import InTechs.InTechs.exception.exceptions.ProjectNotFoundException;
import InTechs.InTechs.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private IssueRepository issueRepository;

    @Override
    public List<CalendarResponse> getCalendar(String issueId) {
        return issueRepository.findById(issueId).stream()
                .map(issue -> CalendarResponse.builder()
                    .name(issue.getWriter())
                    .content(issue.getContent())
                    .date(issue.getEnd_date())
                    .state(issue.getState())
                    .progress(issue.getProgress())
                    .build())
                .collect(Collectors.toList());
    }

}
