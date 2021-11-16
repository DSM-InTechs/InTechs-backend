package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.request.NoticeRequest;
import InTechs.InTechs.channel.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
