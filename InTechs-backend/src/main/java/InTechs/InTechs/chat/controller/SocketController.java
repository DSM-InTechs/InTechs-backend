package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.request.*;
import InTechs.InTechs.chat.service.MessageService;
import InTechs.InTechs.chat.service.SocketService;
import InTechs.InTechs.chat.service.ThreadService;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocketController {

    private final SocketIOServer server;

    private final SocketService socketService;
    private final MessageService messageService;
    private final ThreadService threadService;

    @PostConstruct
    public void SocketMapping() {
        server.addConnectListener(socketService::connect);
        server.addDisconnectListener(socketService::disConnect);

        server.addEventListener("joinChannel", String.class,
                (client, channel, ackSender) -> socketService.joinChannel(client, channel));

        server.addEventListener("send", TextRequest.class,
                (client, data, ackSender) -> socketService.chat(client, data));

        server.addEventListener("delete", ChatDeleteRequest.class,
                ((client, data, ackSender) -> messageService.messageDelete(client, data)));

        server.addEventListener("update", ChatUpdateRequest.class,
                (((client, data, ackSender) -> messageService.messageUpdate(client, data))));

        server.addEventListener("thread", ThreadRequest.class,
                ((client, data, ackSender) -> threadService.thread(client, data)));

        server.addEventListener("emoji", EmojiRequest.class,
                ((client, data, ackSender) -> messageService.emoji(client, data)));
    }

}
