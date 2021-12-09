package InTechs.InTechs.chat.repository;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.ChatType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByChannelId(String channelId, Pageable pageable);
    Optional<Chat> findFirstByNoticeTrueAndChannelIdOrderByNoticeTime(String channelId);

    List<Chat> findByChannelIdAndChatType(String channelId, ChatType chatType);

    List<Chat> findByChannelIdAndNoticeIsTrueOrderByNoticeTime(String channelId);

    List<Chat> findAllByChannelIdAndMessageContaining(String channelId, String message);

    Optional<Chat> findTop1ByChannelIdOrderByTime(String channelId);

}
