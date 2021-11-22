package InTechs.InTechs.chat;

import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatDeleteTests {
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    ChatRepository chatRepository;
    @Test
    public void chatDelete(){
        chatRepository.deleteById("6199005af01dd5754eb4ab7e");
    }
}
