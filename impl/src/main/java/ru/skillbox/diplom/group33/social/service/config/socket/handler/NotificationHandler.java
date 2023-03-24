package ru.skillbox.diplom.group33.social.service.config.socket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.repository.friend.FriendRepository;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode.FRIEND;
import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.*;
import static ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils.getJwtUsersId;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationHandler {

    private final KafkaTemplate<String, Object> template;
    private final NotificationService notificationService;
    private final HashMap<NotificationType, Boolean> settingsMap;
    private final AccountRepository accountRepository;
    private final FriendRepository friendRepository;
    @Value(value = "${topic.names.notify}")
    private String topic;

    public void sendNotification(Long receiverId, Long authorId, NotificationType type, String content) {
        log.info("NotificationHandler: sendNotification {}", receiverId);
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
        log.info("NotificationHandler: sendNotificationReceivers {}", type);
        getAccountsIds().forEach(id -> sendNotification(id, getJwtUsersId(), type, content));
    }

    @Async
    @Scheduled(cron = "0 0 05 * * ?")
    public void checkFriendsBirthDay() {
        log.info("NotificationHandler: checkFriendsBirthDay {}", ZonedDateTime.now());
        int day = ZonedDateTime.now().getDayOfMonth();
        int month = ZonedDateTime.now().getMonthValue();
        accountRepository.findByBirthDate(month, day).forEach(id ->
                friendRepository.findByFromAccountIdAndStatusCode(id, FRIEND).forEach(friend ->
                        sendNotification(friend.getToAccountId(),
                                friend.getFromAccountId(), FRIEND_BIRTHDAY, "Сегодня")));
    }

    public List<Long> getAccountsIds() {
        log.info("NotificationHandler: getAccountsIds");
        Page<Account> pageData = accountRepository.findAll(PageRequest.of(0, 20));
        List<Long> listIds = pageData.getContent().stream()
                .map(Account::getId).collect(Collectors.toList());
        while (pageData.hasNext()) {
            pageData = accountRepository.findAll(pageData.nextPageable());
            listIds.addAll(pageData.getContent().stream()
                    .map(Account::getId).collect(Collectors.toList()));
        }
        return listIds;
    }

    private void initSettingsMap(NotificationSettings settings) {
        settingsMap.put(POST, settings.getPost());
        settingsMap.put(MESSAGE, settings.getMessage());
        settingsMap.put(COMMENT_COMMENT, settings.getCommentComment());
        settingsMap.put(FRIEND_BIRTHDAY, settings.getFriendBirthday());
        settingsMap.put(FRIEND_REQUEST, settings.getFriendRequest());
        settingsMap.put(POST_COMMENT, settings.getPostComment());
        settingsMap.put(SEND_EMAIL_MESSAGE, settings.getSendEmailMessage());
    }
}
