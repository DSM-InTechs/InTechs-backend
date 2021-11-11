package InTechs.InTechs.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    private String sender;

    private boolean notice;

    private String channelId;

    public Chat updateNotice(boolean notice) {
        this.notice = notice;
        return this;
    }

}
