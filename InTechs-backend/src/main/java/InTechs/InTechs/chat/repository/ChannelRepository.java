package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Channel;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, Integer> {

    boolean existsByChannelIdAndUsers(int channelId, User user);

    Optional<Channel> findByChannelIdAndUsersNot(int channelId, User user);

}
