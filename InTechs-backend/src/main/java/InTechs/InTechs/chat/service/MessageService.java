package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.payload.request.ChatDeleteRequest;
import InTechs.InTechs.chat.payload.response.*;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.MessageNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 메세지에 이모티콘
// 메세지 수정
@RequiredArgsConstructor
@Service
public class MessageService {
    private final ChatRepository chatRepository;
    private final SocketIOServer server;

    public void messageDelete(SocketIOClient client, ChatDeleteRequest req){
        if(!client.getAllRooms().contains(req.getChannelId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        changeMessageDelete(req.getMessageId());

        server.getRoomOperations(req.getChannelId()).sendEvent("delete",req.getChannelId());
    }

    private void changeMessageDelete(String messageId){
        Chat chat = chatRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        chat.messageDelete();
        chatRepository.save(chat);
    }

    public ChatsResponse readChat(String email, String channelId, Pageable pageable){
        List<Chat> chats = chatRepository.findByChannelId(channelId,pageable);
        Chat noticeChat = chatRepository.findByNoticeTrueAndChannelId(channelId).orElseGet(()->Chat.builder().build());
        if(noticeChat.getSender()!=null){
            return ChatsResponse.builder()
                    .channelId(channelId)
                    .notice(ChatResponse.builder()
                            .id(String.valueOf(noticeChat.getId()))
                            .message(noticeChat.getMessage())
                            .sender(SenderResponse.builder()
                                    .email(noticeChat.getSender().getEmail())
                                    .name(noticeChat.getSender().getName())
                                    .image(noticeChat.getSender().getImage()).build())
                            .time(noticeChat.getTime())
                            .isDelete(noticeChat.isDeleted())
                            .isMine(email.equals(noticeChat.getSender().getEmail()))
                            .chatType(noticeChat.getChatType()).build())
                    .chats(chatResponsesCreate(chats, email))
                    .build();
        }
        return ChatsResponse.builder()
                .channelId(channelId)
                .chats(chatResponsesCreate(chats, email))
                .build();
    }

    public List<ChatResponse> messageSearch(String email, String channelId, String message){
        List<Chat> chats = chatRepository.findAllByChannelIdAndMessageContaining(channelId, message).stream().filter((c)-> !c.isDeleted()).collect(Collectors.toList());
        return chatResponsesCreate(chats, email);
    }

    private List<ChatResponse> chatResponsesCreate(List<Chat> chats, String email){
        List<ChatResponse> chatResponses = new ArrayList<>();
        for(Chat c:chats){
            chatResponses.add(ChatResponse.builder()
                    .id(c.getId().toString())
                    .message(c.getMessage())
                    .sender(SenderResponse.builder()
                            .email(c.getSender().getEmail())
                            .name(c.getSender().getName())
                            .image(c.getSender().getImage()).build())
                    .time(c.getTime())
                    .isDelete(c.isDeleted())
                    .isMine(email.equals(c.getSender().getEmail()))
                    .chatType(c.getChatType())
                    .build());
        }
        return chatResponses;
    }


    // class로 따로 빼기?
    private void clientDisconnect(SocketIOClient client, Integer status, String reason) {
        client.sendEvent("ERROR", new ErrorResponse(status, reason));
        client.disconnect();
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s - %s%n", client.getSessionId(), reason)
        );
    }

    @SneakyThrows
    private void printLog(SocketIOClient client, String content) {
        String stringDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.printf(
                "%s  %s - [%s] - %s",
                stringDate,
                "SOCKET",
                client.getRemoteAddress().toString().substring(1),
                content
        );
    }
}
