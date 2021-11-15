package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.user.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, ObjectId> {

    List<Chat> findBySenderAndChannelId(String sender, String channelId);

    List<Chat> findByChannelIdAndNoticeIsTrue(String channelId);

    Chat findTop1ByChannelIdOrderByTime(String channelId);

}
