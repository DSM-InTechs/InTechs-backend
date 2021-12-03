package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;

import java.util.List;

public interface NoticeService {

    void updateNotice(String chatId, NoticeRequest noticeRequest);

    List<NoticeResponse> noticeList(String channelId);

}
