package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.entity.Thread;
import InTechs.InTechs.chat.payload.request.ThreadRequest;
import InTechs.InTechs.chat.payload.response.ErrorResponse;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.payload.response.ThreadResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatNotFoundException;
import InTechs.InTechs.user.entity.User;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ThreadService {
    private final ChatRepository chatRepository;
    private final SocketIOServer server;


    public void thread(SocketIOClient client, ThreadRequest req){
        if(!client.getAllRooms().contains(req.getChannelId())) {
            client.sendEvent("ERROR", new ErrorResponse(401, "Invalid Connection"));
            client.disconnect();
            return;
        }
        User user = client.get("user");

        createThread(
                user,
                req.getChatId(),
                req.getMessage()
        );

        server.getRoomOperations(req.getChannelId()).sendEvent(
                "thread", ThreadResponse.builder()
                        .chatId(req.getChatId())
                        .message(req.getMessage())
                        .sender(SenderResponse.builder().email(user.getEmail()).name(user.getName()).image(user.getFileUrl()).build())
                        .time(LocalDateTime.now())
                        .build()
        );

    }

    private void createThread(User user, String chatId, String message){
        Chat chat = chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
        chat.addThread(
                Thread.builder()
                        .message(message)
                        .sender(Sender.builder().email(user.getEmail()).name(user.getName()).image(user.getFileUrl()).build())
                        .time(LocalDateTime.now())
                        .build()
        );
        chatRepository.save(chat);
    }
}
