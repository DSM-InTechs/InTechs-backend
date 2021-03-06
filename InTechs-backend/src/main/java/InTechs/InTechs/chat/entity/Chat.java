package InTechs.InTechs.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class Chat {

    @Id
    private ObjectId id;

    private String message;

    private LocalDateTime time;

    private Sender sender;

    private boolean notice;

    private LocalDateTime noticeTime;

    private String channelId;

    private boolean isDeleted;

    private ChatType chatType;

    private List<Thread> threads = new ArrayList<>();

    private Map<String, EmojiInfo> emojis = new LinkedHashMap<>();

    public Chat updateNotice(boolean notice) {
        this.notice = notice;
        return this;
    }

    public Chat updateNoticeTime(LocalDateTime noticeTime) {
        this.noticeTime = noticeTime;
        return this;
    }

    public void messageDelete(){
        this.isDeleted = true;
    }

    public void addThread(Thread thread){
        threads.add(thread);
    }

    public void messageUpdate(String message){
        this.message = message;
    }

    public void addEmoji(String emoji, Sender sender){
        if(emojis.containsKey(emoji)) {
            emojis.get(emoji).emojiUser(sender);
        } else {
            emojis.put(emoji, createEmojiInfo(sender));
        }
    }

    private EmojiInfo createEmojiInfo(Sender sender){
        Set<Sender> user = new HashSet<>();
        user.add(sender);
        return EmojiInfo.builder().count(1).users(user).build();
    }

}
