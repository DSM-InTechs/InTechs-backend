package InTechs.InTechs.chat;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.chat.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

@SpringBootTest
public class ChatDeleteTests {
    @Autowired
    MessageService messageService;
    @Autowired
    ChatRepository chatRepository;
    @Test
    public void chatDelete(){
        messageService.messageDelete("6198ffcca3450e6e6a87eaaf");
    }

    @Test
    public void dirtyCheckingTest(){
        Chat chat = Chat.builder()
                .message("테스트dd")
                .time(LocalDateTime.now())
                .sender(Sender.builder().email("tpsddej").name("Dddad").image("dfas").build())
                .notice(false)
                .channelId("619757baa528e1194ea7dbc6")
                .isDeleted(false).build();

        chatRepository.save(chat);

        messageService.messageDelete(chat.getId().toString());

        Chat c = chatRepository.findById(chat.getId().toString()).orElseThrow();
        assertThat(c.isDeleted()).isEqualTo(true);

    }
}
