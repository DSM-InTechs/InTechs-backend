package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.payload.response.NoticeResponse;
import InTechs.InTechs.channel.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}/{channelId}")
public class NoticeController {

    private final NoticeService noticeService;

    @PatchMapping("/{chatId}/notice")
    public void setNotice(@PathVariable int projectId,
                          @PathVariable String channelId,
                          @PathVariable String chatId,
                          @RequestBody NoticeRequest noticeRequest) {

        noticeService.updateNotice(projectId, channelId, chatId, noticeRequest);
    }

    @GetMapping("/notices")
    public List<NoticeResponse> getNotices(@PathVariable int projectId,
                                           @PathVariable String channelId) {
        return noticeService.noticeList(projectId, channelId);
    }

}
