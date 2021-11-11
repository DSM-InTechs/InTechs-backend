package InTechs.InTechs.calendar.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@Document(collection = "calendar")
public class Calendar {

    @Id
    int id;

    LocalDate date;

    @Size(max = 50)
    String content;

}
