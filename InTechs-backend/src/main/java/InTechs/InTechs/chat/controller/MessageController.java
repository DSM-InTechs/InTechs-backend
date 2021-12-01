package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.request.FileRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.ChatsResponse;
import InTechs.InTechs.chat.service.ChatService;
import InTechs.InTechs.chat.service.MessageService;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/channel/{channelId}/chat")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final AuthenticationFacade authenticationFacade;
    private final ChatService chatService;

    @GetMapping
    public ChatsResponse readChannelChat(@PathVariable String channelId, final Pageable pageable){
        return messageService.readChat(authenticationFacade.getUserEmail(), channelId, pageable);
    }

    @GetMapping("/{message}")
    public List<ChatResponse> messageSearch(@PathVariable String channelId, @PathVariable String message){
        return messageService.messageSearch(authenticationFacade.getUserEmail(), channelId, message);
    }

    @PostMapping("/file")
    public void fileSend(@PathVariable String channelId, @ModelAttribute FileRequest file) {
        chatService.sendFile(channelId, file);
    }

}
