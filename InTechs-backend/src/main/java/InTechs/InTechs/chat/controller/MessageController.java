package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.ChatsResponse;
import InTechs.InTechs.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/channel/{channelId}/chat")
@RestController
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ChatsResponse readChannelChat(@PathVariable String channelId, final Pageable pageable){
        return messageService.readChat(channelId, pageable);
    }

    @GetMapping("/{message}")
    public List<ChatResponse> messageSearch(@PathVariable String channelId, @PathVariable String message){
        return messageService.messageSearch(channelId, message);
    }

}
