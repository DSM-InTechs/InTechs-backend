package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.request.ChatRequest;
import InTechs.InTechs.chat.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocketContorller {

    private SocketIOServer server;

    private SocketService socketService;

    @PostConstruct
    public void SocketMapping() {
        server.addConnectListener(socketService::connect);
        server.addDisconnectListener(socketService::disConnect);

        server.addEventListener("joinChannel", String.class,
                (client, channel, ackSender) -> socketService.joinChannel(client, channel));

        server.addEventListener("send", ChatRequest.class,
                (client, data, ackSender) -> socketService.chat(client, data));
    }

}
