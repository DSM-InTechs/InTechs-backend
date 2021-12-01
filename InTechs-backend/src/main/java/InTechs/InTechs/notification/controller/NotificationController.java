package InTechs.InTechs.notification.controller;

import InTechs.InTechs.notification.service.NotificationService;
import InTechs.InTechs.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/{channelId}/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final AuthenticationFacade authenticationFacade;

    @PatchMapping
    public void notificationOnAndOff(@PathVariable String channelId){
        notificationService.notificationStateChange(authenticationFacade.getUserEmail(), channelId);
    }
}
