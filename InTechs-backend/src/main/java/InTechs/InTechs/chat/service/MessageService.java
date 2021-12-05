package InTechs.InTechs.chat.service;

import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.entity.EmojiInfo;
import InTechs.InTechs.chat.entity.Sender;
import InTechs.InTechs.chat.entity.Thread;
import InTechs.InTechs.chat.payload.request.ChatDeleteRequest;
import InTechs.InTechs.chat.payload.request.ChatUpdateRequest;
import InTechs.InTechs.chat.payload.request.EmojiRequest;
import InTechs.InTechs.chat.payload.response.*;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.ChatNotFoundException;
import InTechs.InTechs.exception.exceptions.MessageNotFoundException;
import InTechs.InTechs.user.entity.User;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// 메세지에 이모티콘

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

        server.getRoomOperations(req.getChannelId()).sendEvent("delete",req.getMessageId());
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
                            .sender(noticeChat.getSender())
                            .time(noticeChat.getTime().toString())
                            .isDelete(noticeChat.isDeleted())
                            .isMine(email.equals(noticeChat.getSender().getEmail()))
                            .chatType(noticeChat.getChatType())
                            .threads(threadResponsesCreate(noticeChat.getThreads())).build())
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

    public void messageUpdate(SocketIOClient client, ChatUpdateRequest req){
        if(!client.getAllRooms().contains(req.getChannelId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        Chat chat = chatRepository.findById(req.getChatId()).orElseThrow(ChatNotFoundException::new);
        chat.messageUpdate(req.getMessage());
        chatRepository.save(chat);

        server.getRoomOperations(req.getChannelId())
                .sendEvent(
                        "update",
                        ChatResponse.builder()
                        .id(req.getChatId())
                        .message(chat.getMessage())
                        .isDelete(chat.isDeleted())
                        .isMine(true)
                        .chatType(chat.getChatType())
                        .time(chat.getTime().toString())
                        .sender(chat.getSender()).build());
    }

    private List<ChatResponse> chatResponsesCreate(List<Chat> chats, String email){
        List<ChatResponse> chatResponses = new ArrayList<>();
        for(Chat c:chats){
            chatResponses.add(ChatResponse.builder()
                    .id(c.getId().toString())
                    .message(c.getMessage())
                    .sender(c.getSender())
                    .time(c.getTime().toString())
                    .isDelete(c.isDeleted())
                    .isMine(email.equals(c.getSender().getEmail()))
                    .chatType(c.getChatType())
                    .threads(threadResponsesCreate(c.getThreads()))
                    .emojis(emojiCreate(c))
                    .build());
        }
        return chatResponses;
    }

    private List<ThreadResponse> threadResponsesCreate(List<Thread> threads){
        List<ThreadResponse> threadResponses = new ArrayList<>();
        if(threads==null){
            threadResponses.add(ThreadResponse.builder().build());
            return threadResponses;
        }
        for(Thread t : threads){
            threadResponses.add(
                    ThreadResponse.builder()
                                .message(t.getMessage())
                                .sender(SenderResponse.builder().email(t.getSender().getEmail()).name(t.getSender().getName()).image(t.getSender().getImage()).build())
                                .time(t.getTime()).build()
            );
        }
        return threadResponses;
    }

    public void emoji(SocketIOClient client, EmojiRequest req){
        if(!client.getAllRooms().contains(req.getChannelId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }
        User user = client.get("user");

        Chat chat = chatRepository.findById(req.getChatId()).orElseThrow(ChatNotFoundException::new);

        Sender sender = Sender.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .image(user.getFileUrl()).build();
        chat.addEmoji(req.getEmojiName(), sender);
        chatRepository.save(chat);

        server.getRoomOperations(req.getChannelId())
                .sendEvent(
                        "emoji",
                        ChatResponse.builder()
                                .id(req.getChatId())
                                .message(chat.getMessage())
                                .isDelete(chat.isDeleted())
                                .isMine(true)
                                .chatType(chat.getChatType())
                                .time(chat.getTime().toString())
                                .sender(chat.getSender())
                                .emojis(emojiCreate(chat))
                                .build());
    }

    private Map<String, EmojiInfoResponse> emojiCreate(Chat chat){
        Map<String, EmojiInfoResponse> emojis = new HashMap<>();
        for(String key : chat.getEmojis().keySet()){
            EmojiInfo emojiInfo = chat.getEmojis().get(key);
            emojis.put(key, EmojiInfoResponse.builder()
                                            .count(emojiInfo.getCount())
                                            .users(createSenderResponse(emojiInfo.getUsers()))
                                            .build());
        }
        return emojis;
    }

    private Set<SenderResponse> createSenderResponse(Set<Sender> users){
        Set<SenderResponse> senders = new HashSet<>();
        for(Sender s : users){
            senders.add(SenderResponse.builder()
                                    .email(s.getEmail())
                                    .name(s.getName())
                                    .image(s.getImage())
                                    .build());
        }
        return senders;
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
