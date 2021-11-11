package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Channel;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {

    Boolean existsByChannelIdAndUsersContaining(String channelId, User user);

}
