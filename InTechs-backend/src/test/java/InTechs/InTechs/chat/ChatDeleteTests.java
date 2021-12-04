package InTechs.InTechs.chat;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.chat.service.MessageService;
import InTechs.InTechs.exception.exceptions.ChatNotFoundException;
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
    public void chatCreateTest(){
        Chat chat = Chat.builder()
                .message("테스트dd")
                .time(LocalDateTime.now())
                .sender(Sender.builder().email("tpsddej").name("Dddad").image("dfas").build())
                .notice(true)
                .channelId("619757baa528e1194ea7dbc6")
                .isDeleted(false).build();

        chatRepository.save(chat);

        //messageService.messageDelete(chat.getId().toString());

        Chat c = chatRepository.findById(chat.getId().toString()).orElseThrow();
        assertThat(c.getChannelId()).isEqualTo("619757baa528e1194ea7dbc6");

    }

    @Test
    public void chatUpdateTest(){
        Chat chat = chatRepository.findById("61a78386685a225d9bb30b5a").orElseThrow(ChatNotFoundException::new);
        chat.messageUpdate("수정된 메세지");
        chatRepository.save(chat);

        Chat c = chatRepository.findById("61a78386685a225d9bb30b5a").orElseThrow();
        assertThat(c.getMessage()).isEqualTo("수정된 메세지");

    }
}
