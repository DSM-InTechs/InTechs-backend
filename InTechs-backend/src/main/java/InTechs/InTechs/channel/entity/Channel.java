package InTechs.InTechs.channel.entity;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.user.entity.User;
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

    @DBRef(lazy = true)
    private List<User> users;

    @DBRef(lazy = true)
    private List<Chat> Chats;

    private int projectId;

    public Channel updateName(String name) {
        this.name = name;
        return this;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

}
