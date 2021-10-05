package InTechs.InTechs.calendar.repository;

import InTechs.InTechs.calendar.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar, String> {

}
