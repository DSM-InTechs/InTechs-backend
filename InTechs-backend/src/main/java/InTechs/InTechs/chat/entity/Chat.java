package InTechs.InTechs.chat.entity;

import InTechs.InTechs.user.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "chat")
public class Chat {

    @Id
    private int id;

    private String message;

    private LocalDateTime time;

    private String sender;

    private boolean notice = false;

    @DBRef(lazy = true)
    private List<User> users;

    private String channelId;

    private int projectId;

    public Chat updateNotice(boolean notice) {
        this.notice = notice;
        return this;
    }

}
