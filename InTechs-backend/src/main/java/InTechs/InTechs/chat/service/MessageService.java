package InTechs.InTechs.chat.service;

import InTechs.InTechs.channel.entity.Channel;
import InTechs.InTechs.channel.repository.ChannelRepository;
import InTechs.InTechs.chat.entity.Chat;
import InTechs.InTechs.chat.payload.request.ChatDeleteRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.ChatsResponse;
import InTechs.InTechs.chat.payload.response.ErrorResponse;
import InTechs.InTechs.chat.payload.response.SenderResponse;
import InTechs.InTechs.chat.repository.ChatRepository;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.file.FileUploader;
import InTechs.InTechs.user.entity.User;
import InTechs.InTechs.user.repository.UserRepository;
import com.corundumstudio.socketio.SocketIOClient;
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
// 메세지 삭제
@RequiredArgsConstructor
@Service
public class MessageService {
    private final ChatRepository chatRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final FileUploader fileUploader;

    public void messageDelete(SocketIOClient client, ChatDeleteRequest req){
        if(!client.getAllRooms().contains(req.getChannelId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        Channel channel = channelRepository.findById(req.getChannelId()).orElseThrow();
        channel.deleteChat(chatRepository.findById(req.getMessageId()).orElseThrow());

        chatRepository.deleteById(req.getMessageId());

        //server.getRoomOperations(req.getChannelId()).sendEvent("delete");
    }

    public ChatsResponse readChat(String channelId, Pageable pageable){
        List<Chat> chatList = chatRepository.findByChannelId(channelId,pageable);
        Chat noticeChat = chatRepository.findByNoticeTrueAndChannelId(channelId).orElseGet(()->Chat.builder().build());
        User noticeSender = userRepository.findById(noticeChat.getSender().getEmail()).orElseThrow(UserNotFoundException::new);
        List<ChatResponse> chats = new ArrayList<>();
        for(Chat c : chatList){
            User sender = userRepository.findById(c.getSender().getEmail()).orElseThrow(UserNotFoundException::new); // chat에 Sender저장해서 해결하기
            chats.add(ChatResponse.builder()
                    .id(c.getId().toString())
                    .message(c.getMessage())
                    .sender(SenderResponse.builder().email(sender.getEmail()).name(sender.getName()).image(sender.getFileName()).build())
                    .time(c.getTime()).build());
        }
        return ChatsResponse.builder()
                .channelId(channelId)
                .notice(ChatResponse.builder()
                        .id(String.valueOf(noticeChat.getId()))
                        .message(noticeChat.getMessage())
                        .sender(SenderResponse.builder()
                                .email(noticeChat.getSender().getEmail())
                                .name(noticeSender.getName())
                                .image(imageUrl(noticeSender.getFileName())).build())
                        .time(noticeChat.getTime()).build())
                .chats(chats)
                .build();
    }


    private String imageUrl(String fileName) {
        String fileUrl = fileUploader.getObjectUrl(fileName);

        if(fileUrl == null) {
            fileUrl = fileUploader.getObjectUrl("인덱스 프로필.jpg");
        }
        return fileUrl;
    }

    public List<ChatResponse> messageSearch(String email, String channelId, String message){
        List<Chat> chats = chatRepository.findAllByChannelIdAndMessageContaining(channelId, message).stream().filter((c)-> !c.isDeleted()).collect(Collectors.toList());
        List<ChatResponse> chatResponses = new ArrayList<>();
        for(Chat c:chats){
            chatResponses.add(ChatResponse.builder()
                                        .id(c.getId().toString())
                                        .message(c.getMessage())
                                        .sender(SenderResponse.builder()
                                                    .email(c.getSender().getEmail())
                                                    .name("아이쿠")
                                                    .image("얼른").build())
                                        .time(c.getTime())
                                        .isMine(c.getSender().getEmail().equals(email))
                                        .isDelete(c.isDeleted())
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
