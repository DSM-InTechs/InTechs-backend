package InTechs.InTechs.chat.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "chat")
public class Chat {

    @Id
    private int id;

    private final int channelId;

    private String message;

    private LocalDateTime time;

    private String sender;

}
