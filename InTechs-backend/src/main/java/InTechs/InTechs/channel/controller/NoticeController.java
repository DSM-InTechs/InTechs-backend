package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;
import InTechs.InTechs.channel.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class NoticeController {

    private final NoticeService noticeService;

    @PatchMapping("/{chatId}/notice")
    public void setNotice(@PathVariable String chatId,
                          @RequestBody NoticeRequest noticeRequest) {

        noticeService.updateNotice(chatId, noticeRequest);
    }

    @GetMapping("/{channelId}/notice")
    public NoticeResponse currentNotice(@PathVariable String channelId) {
        return noticeService.currentNotice(channelId);
    }

    @GetMapping("/{channelId}/notices")
    public List<NoticeResponse> getNotices(@PathVariable String channelId) {
        return noticeService.noticeList(channelId);
    }

}
