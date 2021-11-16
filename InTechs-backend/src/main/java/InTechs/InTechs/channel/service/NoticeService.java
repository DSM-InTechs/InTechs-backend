package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.NoticeResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface NoticeService {

    void updateNotice(String chatId, NoticeRequest noticeRequest);

    NoticeResponse currentNotice(String channelId);

    List<NoticeResponse> noticeList(String channelId);

}
