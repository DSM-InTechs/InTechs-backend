package InTechs.InTechs.chat;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmojiTests {
    @Autowired
    ChatRepository chatRepository;

    @Test
    public void emojiPutTest(){
        Chat chat = chatRepository.findById("61a78386685a225d9bb30b5a").orElseThrow();
        Sender sender = Sender.builder().email("1@gmail.com").name("z").image("Z").build();
        chat.addEmoji("이모지이름", sender);
        chatRepository.save(chat);
    }
}
