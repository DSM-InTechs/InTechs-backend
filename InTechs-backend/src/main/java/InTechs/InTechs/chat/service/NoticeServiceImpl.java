package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private ChatRepository chatRepository;

    @Override
    public void updateNotice(int chatId, NoticeRequest noticeRequest) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(ChatChannelNotFoundException::new);

        chatRepository.save(chat.updateNotice(noticeRequest.isNotice()));
    }

    @Override
    public List<ChatResponse> noticeList(String channelId) {
        return chatRepository.findByChannelIdAndNoticeIsTrueOrderByTime(channelId).stream()
                .map(chat -> ChatResponse.builder()
                        .sender(chat.getSender())
                        .message(chat.getMessage())
                        .time(chat.getTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ChatResponse currentNotice(String channelId) {
        List<Chat> notices = chatRepository.findByChannelIdAndNoticeIsTrueOrderByTime(channelId);

        Chat current = chatRepository.findById(notices.get(0).getId())
                .orElseThrow(ChatChannelNotFoundException::new);

        return ChatResponse.builder()
                .message(current.getMessage())
                .sender(current.getSender())
                .time(current.getTime())
                .build();
    }

    @Override
    public ChatResponse detailNotice(int chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(ChatChannelNotFoundException::new);

        return null;
    }

}
