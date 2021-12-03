package InTechs.InTechs.channel.controller;

import InTechs.InTechs.channel.payload.request.ChannelRequest;
import InTechs.InTechs.channel.payload.request.UpdateChannelRequest;
import InTechs.InTechs.channel.payload.response.ChannelIdResponse;
import InTechs.InTechs.channel.payload.response.ChannelResponse;
import InTechs.InTechs.channel.service.ChannelService;
import InTechs.InTechs.user.payload.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{projectId}")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ChannelIdResponse createChannel(@PathVariable int projectId,
                                @RequestBody ChannelRequest channelRequest) {
        return channelService.createChannel(projectId, channelRequest);
    }

    @PatchMapping("/{channelId}")
    public void updateChannel(@PathVariable int projectId,
                              @PathVariable String channelId,
                              @ModelAttribute @Valid UpdateChannelRequest updateChannelRequest) throws IOException {

        channelService.updateChannel(projectId, channelId, updateChannelRequest);
    }

    @DeleteMapping("/{channelId}")
    public void deleteChannel(@PathVariable int projectId,
                              @PathVariable String channelId) {
        channelService.deleteChannel(projectId, channelId);
    }

    @PostMapping("/{channelId}/{email}")
    public void addUser(@PathVariable int projectId,
                        @PathVariable String channelId,
                        @PathVariable String email) {
        channelService.AddUser(projectId, email, channelId);
    }

    @PatchMapping("/{channelId}/user")
    public void exitUser(@PathVariable int projectId,
                         @PathVariable String channelId) {
        channelService.exitChannel(projectId, channelId);
    }

    @GetMapping("/{channelId}/users")
    public List<ProfileResponse> getProfiles(@PathVariable int projectId,
                                             @PathVariable  String channelId) {
        return channelService.getProfiles(projectId, channelId);
    }

    @GetMapping("/channels")
    public List<ChannelResponse> getChannels(@PathVariable int projectId) {
        return channelService.getChannels(projectId);
    }

}
