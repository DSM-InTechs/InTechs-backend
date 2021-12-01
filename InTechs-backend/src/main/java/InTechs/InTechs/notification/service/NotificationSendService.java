package InTechs.InTechs.notification.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationSendService {

    public void sendTargetMessage(String targetToken, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message message = Message.builder()
                .setToken(targetToken)
                .setApnsConfig(ApnsConfig.builder()
                        .putCustomData("title", title)
                        .putCustomData("body", body).build())
                .setNotification(notification).build();

        FirebaseMessaging.getInstance().send(message);
    }

    public void sendTargetsMessage(List<String> targetTokens, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        MulticastMessage message = MulticastMessage.builder()
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setAlert(ApsAlert.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build())
                                .setContentAvailable(true)
                                .setMutableContent(false)
                                .build())
                        .putCustomData("title", title)
                        .putCustomData("body", body).build())
                .setNotification(notification)
                .addAllTokens(targetTokens)
                .build();
        FirebaseMessaging.getInstance().sendMulticast(message);
    }

}
