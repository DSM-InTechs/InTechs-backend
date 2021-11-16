package InTechs.InTechs.user.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@EqualsAndHashCode(of = "user")
public class ChannelUser {
    @DBRef
    private User user;

    private boolean isNotificationAllow;

    @Builder
    protected ChannelUser(User user){
        this.user = user;
        isNotificationAllow = true;
    }
}
