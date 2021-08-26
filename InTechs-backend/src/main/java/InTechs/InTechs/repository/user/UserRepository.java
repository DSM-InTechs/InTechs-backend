package InTechs.InTechs.repository.user;

import InTechs.InTechs.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
