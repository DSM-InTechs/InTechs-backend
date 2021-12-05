package InTechs.InTechs.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private String channelId;

    private boolean isDeleted;

    private ChatType chatType;

    private List<Thread> threads = new ArrayList<>();

    private Map<String, EmojiInfo> emojis = new LinkedHashMap<>();

    public Chat updateNotice(boolean notice) {
        this.notice = notice;
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
        if(emojis.containsKey(emoji)){
            emojis.get(emoji).emojiUser(sender);
        }
        // 없을경우 처음 등록
    }

}
