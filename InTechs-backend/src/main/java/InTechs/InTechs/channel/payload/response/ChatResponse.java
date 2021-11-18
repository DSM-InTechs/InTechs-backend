package InTechs.InTechs.channel.payload.response;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
public class ChatResponse {
    final private ObjectId id;
    final private String message;
    final private LocalDateTime time;
    final private String sender;
    final private boolean notice;
}
