package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.payload.request.NoticeRequest;

public interface NoticeService {

    void updateNotice(String chatId, NoticeRequest noticeRequest);

}
