package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.payload.request.ChatRequest;
import com.corundumstudio.socketio.SocketIOClient;

public interface SocketService {
    void connect(SocketIOClient client);
    void disConnect(SocketIOClient client);
    void joinChannel(SocketIOClient client, String channelId);
    void chat(SocketIOClient client, ChatRequest chatRequest);
    void messageDelete(SocketIOClient client, String id);
}
