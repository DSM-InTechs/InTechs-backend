package InTechs.InTechs.notification;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {
    public void sendTargetMessage(String targetToken, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(notification).build();

        FirebaseMessaging.getInstance().send(message);
    }

    public void sendTargetsMessage(List<String> targetTokens, String title, String body, String image) throws FirebaseMessagingException {
        Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();
        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(targetTokens)
                .build();
        FirebaseMessaging.getInstance().sendMulticast(message);
    }

}
