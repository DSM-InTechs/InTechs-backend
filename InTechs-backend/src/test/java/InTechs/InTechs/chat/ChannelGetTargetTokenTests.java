package InTechs.InTechs.chat;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.user.entity.ChannelUser;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@SpringBootTest
public class ChannelGetTargetTokenTests {
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;
    @Test
    public void getTargetToken(){
        Channel channel = channelRepository.findById("619757baa528e1194ea7dbc6").orElseThrow(ChatChannelNotFoundException::new); // 머지 후 채널 익셉션으로 변경
        List<String> targetTokens = channel.getUsers().stream().filter(ChannelUser::isNotificationAllow).map(tu -> tu.getUser().getTargetToken()).collect(Collectors.toList());
        List<String> test = new ArrayList<>();
        test.add("test");
        assertThat(targetTokens).isEqualTo(test);
    }

    @Test
    public void createChannel(){
        ArrayList<ChannelUser> cu = new ArrayList<>();
        User user = userRepository.findById("okk@gmail.com").orElseThrow();
        cu.add(ChannelUser.builder().user(user).build());
        channelRepository.save(Channel.builder().name("채널").projectId(811487).users(cu).build());
    }

    @Test
    public void createChat(){
        Chat chat = Chat.builder()
                .message("테스트dd")
                .time(LocalDateTime.now())
                .sender(Sender.builder().email("tpsddej").name("Dddad").image("dfas").build())
                .notice(false)
                .channelId("619757baa528e1194ea7dbc6")
                .isDeleted(false).build();
        chatRepository.save(chat);
    }
}
