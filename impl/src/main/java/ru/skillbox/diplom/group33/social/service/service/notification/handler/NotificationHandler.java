package ru.skillbox.diplom.group33.social.service.service.notification.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;

import java.util.HashMap;

import static ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils.getJwtUsersId;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationHandler {

    private final KafkaTemplate<String, Object> template;
    private final NotificationService notificationService;
    private final HashMap<NotificationType, Boolean> settingsMap;
    private final AccountService accountService;
    @Value(value = "${topic.names.notify}")
    private String topic;

    public void sendNotification(Long receiverId, Long authorId, NotificationType type, String content) {
        log.info("NotificationHandler sendNotification {}", receiverId);
        initSettingsMap(notificationService.getSettings(receiverId));
        if (settingsMap.get(type)) {
            NotificationInputDto input = new NotificationInputDto();
            input.setNotificationType(type);
            input.setAuthorId(authorId);
            input.setUserId(receiverId);
            input.setContent(content);
            template.send(topic, type.toString(), input);
        }
    }

    public void sendNotificationReceivers(NotificationType type, String content) {
        log.info("NotificationHandler sendNotificationReceivers {}", type);
        accountService.getAccountsIds()
                .forEach(id -> sendNotification(id, getJwtUsersId(), type, content));
    }

    private void initSettingsMap(NotificationSettings settings) {
        settingsMap.put(NotificationType.POST, settings.getPost());
        settingsMap.put(NotificationType.MESSAGE, settings.getMessage());
        settingsMap.put(NotificationType.COMMENT_COMMENT, settings.getCommentComment());
        settingsMap.put(NotificationType.FRIEND_BIRTHDAY, settings.getFriendBirthday());
        settingsMap.put(NotificationType.FRIEND_REQUEST, settings.getFriendRequest());
        settingsMap.put(NotificationType.POST_COMMENT, settings.getPostComment());
        settingsMap.put(NotificationType.SEND_EMAIL_MESSAGE, settings.getSendEmailMessage());
    }
}
