package InTechs.InTechs.channel.service;

import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;

import java.util.List;

public interface NoticeService {

    void updateNotice(int projectId, String channelId, String chatId, NoticeRequest noticeRequest);

    NoticeResponse currentNotice(int projectId, String channelId);

    List<NoticeResponse> noticeList(int projectId, String channelId);

}
