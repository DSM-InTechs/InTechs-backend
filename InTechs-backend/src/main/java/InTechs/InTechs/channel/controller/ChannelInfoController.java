package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.response.ChannelInfoResponse;
import InTechs.InTechs.channel.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/channel/{channelId}")
@RestController
public class ChannelInfoController {
    private final ChannelService channelService;

    @GetMapping
    public ChannelInfoResponse partnerInfo(@PathVariable String channelId){
        return channelService.channelInfo(channelId);
    }
}
