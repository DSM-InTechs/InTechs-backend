package InTechs.InTechs.channel.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import InTechs.InTechs.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public void updateNotice(int projectId, String channelId, String chatId, NoticeRequest noticeRequest) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(ChatChannelNotFoundException::new);

        Boolean notice = noticeRequest.getNotice();

        chatRepository.save(chat.updateNotice(notice));
    }

    @Override
    public NoticeResponse currentNotice(int projectId, String channelId) {
        Chat notice = chatRepository.findTop1ByChannelIdOrderByTime(channelId)
                .orElseThrow(RuntimeException::new);

        User user = userRepository.findByEmail(notice.getSender().getEmail())
                .orElseThrow(UserNotFoundException::new);

        return NoticeResponse.builder()
                .name(user.getName())
                .time(notice.getTime())
                .message(notice.getMessage())
                .build();
    }

    @Override
    public List<NoticeResponse> noticeList(int projectId, String channelId) {
        return chatRepository.findByChannelIdAndNoticeIsTrue(channelId).stream()
                .map(chat -> NoticeResponse.builder()
                        .name(chat.getSender().getName())
                        .time(chat.getTime())
                        .message(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }

}