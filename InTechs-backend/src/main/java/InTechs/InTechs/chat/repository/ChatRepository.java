package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findBySenderAndChannelId(String sender, String channelId);
    List<Chat> findByChannelId(String channelId, Pageable pageable);
    Optional<Chat> findByNoticeTrueAndChannelId(String channelId);

    List<Chat> findByChannelIdAndNoticeIsTrue(String channelId);

    List<Chat> findAllByChannelIdAndMessageContaining(String channelId, String message);

    Optional<Chat> findTop1ByChannelIdOrderByTime(String channelId);

}
