package InTechs.InTechs.chat.controller;

import InTechs.InTechs.chat.payload.request.NoticeRequest;
import InTechs.InTechs.chat.payload.response.ChatResponse;
import InTechs.InTechs.chat.payload.response.NoticeResponse;
import InTechs.InTechs.chat.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class NoticeController {

    private final NoticeService noticeService;

    @PatchMapping("/{chatId}")
    public void setNotice(@PathVariable String chatId,
                          @RequestBody NoticeRequest noticeRequest) {

        noticeService.updateNotice(chatId, noticeRequest);
    }
//
//    @GetMapping("/{channelId}/notices")
//    public List<ChatResponse> getNotices(@PathVariable String channelId) {
//        return noticeService.noticeList(channelId);
//    }
//
    @GetMapping("/{channelId}")
    public NoticeResponse currentNotice(@PathVariable String channelId) {
        return noticeService.currentNotice(channelId);
    }

}
