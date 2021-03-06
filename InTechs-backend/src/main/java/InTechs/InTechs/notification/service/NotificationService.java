package InTechs.InTechs.notification.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.exception.exceptions.ChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    /*
       안녕 종은아 오랜만이야... 이렇게 문자로 남기려니 어색하네...
       벌써 2학년도 끝나가고 곧 3학년이 될텐데 취업준비는 잘 되가니..?
       너가 취업하더라도 꼭 연락하며 친하게 지냈으면 좋겠다...../..
       앞으로 남은 1년도 잘 부탁하고 싸우지 않고 친하게 지내면 좋겠어..
       항상 너에게 고맙고 미안해...
                                            2021.11.24
                                          -익명의 누군가...-
    */
    public void notificationStateChange(String email, String channelId){
       Channel channel =  channelRepository.findById(channelId).orElseThrow(ChannelNotFoundException::new);
       User user = userRepository.findById(email).orElseThrow(UserNotFoundException::new);

       if (channel.getNotificationOnUsers().contains(user)){
           channel.notificationOff(user);
       } else {
           channel.notificationOn(user);
       }

       channelRepository.save(channel);
    }
}
