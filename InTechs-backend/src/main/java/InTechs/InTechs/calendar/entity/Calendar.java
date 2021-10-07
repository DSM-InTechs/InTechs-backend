package InTechs.InTechs.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Document(collection = "calendar")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

    @Id
    int id;

    LocalDate date;

    @Size(max = 50)
    String content;

}
