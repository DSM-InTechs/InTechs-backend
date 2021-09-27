package InTechs.InTechs.calendar.payload.response;

import InTechs.InTechs.issue.value.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@Builder
@AllArgsConstructor
public class CalendarFilterResponse {

    private final String id;
    private final String name;
    private final int progress;
    private final State state;
    private final String date;
    private final String content;

}
