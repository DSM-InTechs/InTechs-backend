package InTechs.InTechs.chat.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class EmojiInfo {
    private int count; // 이모지 갯수
    private Set<Sender> users; // 이모지 단 유저

    public void emojiUser(Sender user){
        if(users.contains(user)){
            users.remove(user);
            this.count--;
        } else{
            users.add(user);
            this.count++;
        }
    }
}
