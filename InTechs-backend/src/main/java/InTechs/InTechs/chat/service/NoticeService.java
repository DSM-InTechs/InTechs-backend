package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;

import java.util.List;

public interface NoticeService {

    void updateNotice(int chatId, NoticeRequest noticeRequest);

    List<ChatResponse> noticeList(String channelId);

    ChatResponse currentNotice(String channelId);

    ChatResponse detailNotice(int chatId);

}
