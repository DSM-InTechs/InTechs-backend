package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.request.ChannelRequest;
import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.service.ChatService;
import InTechs.InTechs.chat.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final NoticeService noticeService;

    @PatchMapping("/{channelId}")
    public void updateChannelName(@PathVariable String channelId,
                                  @RequestBody ChannelRequest channelRequest) {

        chatService.updateChannel(channelId, channelRequest);
    }

    @PatchMapping("/{chatId}")
    public void setNotice(@PathVariable int chatId,
                          @RequestBody NoticeRequest noticeRequest) {

        noticeService.updateNotice(chatId, noticeRequest);
    }

    @GetMapping("/{channelId}/notices")
    public List<ChatResponse> noticeList(@PathVariable String channelId) {

        return noticeService.noticeList(channelId);
    }

    @GetMapping("/{channelId}")
    public ChatResponse currentNotice(@PathVariable String channelId) {

        return noticeService.currentNotice(channelId);
    }

}
