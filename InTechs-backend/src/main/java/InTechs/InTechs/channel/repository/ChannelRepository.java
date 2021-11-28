package InTechs.InTechs.channel.repository;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {
    Boolean existsByChannelIdAndUsersContaining(String channelId, User user);

    List<Channel> findByUsersContains(User user);

}
