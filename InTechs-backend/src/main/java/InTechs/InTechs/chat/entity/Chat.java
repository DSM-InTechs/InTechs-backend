package InTechs.InTechs.chat.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
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
