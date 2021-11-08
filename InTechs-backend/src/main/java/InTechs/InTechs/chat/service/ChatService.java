package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.ChannelRequest;
import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.user.entity.User;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getChatList(String channelId);

    void sendChat(User user, String channelId, String message);

    void updateChannel(String channelId, ChannelRequest channelRequest);

}
