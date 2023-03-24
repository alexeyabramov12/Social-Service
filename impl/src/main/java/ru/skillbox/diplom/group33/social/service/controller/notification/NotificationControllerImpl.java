package ru.skillbox.diplom.group33.social.service.controller.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationSettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.SentNotificationsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.DefaultResponse;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationCountResponse;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<NotificationSettingsDto> getSettings() {
        log.info("NotificationControllerImpl: getSettings");
        return ResponseEntity.ok(notificationService.getSettings());
    }

    @Override
    public ResponseEntity<DefaultResponse> updateSettings(@RequestBody SettingsRequest settings) {
        log.info("NotificationControllerImpl: updateSettings {}", settings);
        return ResponseEntity.ok(notificationService.updateSetting(settings));
    }

    @Override
    public ResponseEntity<SentNotificationsDto> getNotifications() {
        log.info("NotificationControllerImpl: getNotifications");
        return ResponseEntity.ok(notificationService.getNotifications());
    }

    @Override
    public ResponseEntity<NotificationCountResponse> getCountNotifications() {
        log.info("NotificationControllerImpl: getCountNotifications");
        return ResponseEntity.ok(notificationService.countNotifications());
    }
}
