package InTechs.InTechs.user.repository;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    User findByEmailAndChannels(String email,Channel channel);
}
