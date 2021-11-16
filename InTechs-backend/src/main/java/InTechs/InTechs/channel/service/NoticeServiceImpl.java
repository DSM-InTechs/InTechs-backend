package InTechs.InTechs.channel.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public void updateNotice(String chatId, NoticeRequest noticeRequest) {
        Chat chat = chatRepository.findById(new ObjectId(chatId))
                .orElseThrow(ChatChannelNotFoundException::new);

        Boolean notice = noticeRequest.getNotice();

        chatRepository.save(chat.updateNotice(notice));
    }

    @Override
    public NoticeResponse currentNotice(String channelId) {
        Chat notice = chatRepository.findTop1ByChannelIdOrderByTime(channelId);

        User user = userRepository.findByEmail(notice.getSender())
                .orElseThrow(UserNotFoundException::new);

        return NoticeResponse.builder()
                    .name(user.getName())
                    .time(notice.getTime())
                    .message(notice.getMessage())
                .build();
    }

    @Override
    public List<NoticeResponse> noticeList(String channelId) {

        return chatRepository.findByChannelIdAndNoticeIsTrue(channelId).stream()
                .map(chat -> NoticeResponse.builder()
                        .name(chat.getSender())
                        .time(chat.getTime())
                        .message(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }

}
