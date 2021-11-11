package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatChannelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final ChatRepository chatRepository;

    @Override
    public void updateNotice(String chatId, NoticeRequest noticeRequest) {
        Chat chat = chatRepository.findById(new ObjectId(chatId))
                .orElseThrow(ChatChannelNotFoundException::new);

        Boolean notice = noticeRequest.getNotice();

        chatRepository.save(chat.updateNotice(notice));
    }

}
