package InTechs.InTechs.channel.entity;

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
@Document(collection = "channel")
public class Channel {

    @Id
    private String channelId;

    private String name;

    @DBRef
    private List<User> notificationOnUsers;

    @DBRef
    private List<User> users;

    private int projectId;

    private String fileUrl;

    private boolean isDM;

    private LocalDateTime time;

    public Channel updateName(String name) {
        this.name = name;
        return this;
    }

    public Channel updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    public void notificationOff(User user){
        this.notificationOnUsers.remove(user);
    }

    public void notificationOn(User user){
        this.notificationOnUsers.add(user);
    }

}
