package InTechs.InTechs.channel.entity;

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

    @DBRef
    private List<User> notificationOnUsers;

    @DBRef
    private List<User> users;

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

    public void notificationOnAndOff(User user){
//        users.add(user.notificationOnAndOff());
        // List에서 ChannelUser을 찾아서 그 채너유저의 notification과 반대되는 걸로 수정 후
        // 다시 저장?
    }

}
