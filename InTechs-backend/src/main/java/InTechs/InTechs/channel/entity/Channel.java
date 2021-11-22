package InTechs.InTechs.channel.entity;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.user.entity.ChannelUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "channel")
public class Channel {

    @Id
    private String channelId;

    private String name;

    private List<ChannelUser> users;

    private int projectId;

    public Channel updateName(String name) {
        this.name = name;
        return this;
    }

    public void addUser(ChannelUser user) {
        this.users.add(user);
    }

    public void deleteUser(ChannelUser user) {
        this.users.remove(user);
    }

}
