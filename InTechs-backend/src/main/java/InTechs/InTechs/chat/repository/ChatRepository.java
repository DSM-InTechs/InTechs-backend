package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, Integer> {

    List<Chat> findBySenderAndChannelId(String sender, String channelId);

    List<Chat> findByChannelIdAndNoticeIsTrueOrderByTime(String channelId);



}
