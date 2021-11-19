package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.response.ChatsResponse;
import InTechs.InTechs.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/channel/{channelId}/chat/{message}}")
    public void messageSearch(@PathVariable String channelId, @PathVariable String message){
        messageService.messageSearch(channelId, message);
    }

    @GetMapping("/{channelId}/chat")
    public ChatsResponse readChannelChat(@PathVariable String channelId, final Pageable pageable){
        return messageService.readChat(channelId, pageable);
    }

}
