package InTechs.InTechs.channel.payload.response;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
public class ChatResponse {
    private ObjectId id;
    private String message;
    private LocalDateTime time;
    private String sender;
    private boolean notice;
}
