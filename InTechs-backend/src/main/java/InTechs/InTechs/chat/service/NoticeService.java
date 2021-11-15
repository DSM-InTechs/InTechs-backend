package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface NoticeService {

    void updateNotice(String chatId, NoticeRequest noticeRequest);

    ChatResponse currentNotice(String channelId);

    List<ChatResponse> noticeList(String channelId);

}
