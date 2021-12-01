package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.FileRequest;
import InTechs.InTechs.user.entity.User;

public interface ChatService {

    void sendChat(User user, String channelId, String message);

    void sendFile(String channelId, FileRequest fileRequest);

}
