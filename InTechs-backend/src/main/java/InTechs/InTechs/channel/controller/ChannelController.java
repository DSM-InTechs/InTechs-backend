package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.payload.response.ChatsResponse;
import InTechs.InTechs.channel.service.ChannelService;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/{projectId}")
    public void createChannel(@PathVariable int projectId,
                              @RequestBody ChannelRequest channelRequest) {
        channelService.createChannel(projectId, channelRequest);
    }

    @PatchMapping("/{channelId}")
    public void updateChannelName(@PathVariable String channelId,
                                  @RequestBody ChannelRequest channelRequest) {

        channelService.updateChannel(channelId, channelRequest);
    }

    @DeleteMapping("/{channelId}")
    public void deleteChannel(@PathVariable String channelId) {
        channelService.deleteChannel(channelId);
    }

    @PostMapping("/{channelId}/{email}")
    public void addUser(@PathVariable String channelId,
                        @PathVariable String email) {
        channelService.AddUser(email, channelId);
    }

    @PatchMapping("/{channelId}/user")
    public void exitUser(@PathVariable String channelId) {
        channelService.exitChannel(channelId);
    }

    @GetMapping("/{channelId}/users")
    public List<ProfileResponse> getProfiles(@PathVariable  String channelId) {
        return channelService.getProfiles(channelId);
    }

    @GetMapping("/{channelId}/chat")
    public ChatsResponse readChannelChat(@PathVariable String channelId, final Pageable pageable){
        return channelService.readChat(channelId, pageable);
    }
}
