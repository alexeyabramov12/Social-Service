package ru.skillbox.diplom.group33.social.service.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationSettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.SentNotificationsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.*;
import ru.skillbox.diplom.group33.social.service.mapper.notification.NotificationMapper;
import ru.skillbox.diplom.group33.social.service.mapper.notification.NotificationSettingsMapper;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.repository.notification.NotificationRepository;
import ru.skillbox.diplom.group33.social.service.repository.notification.NotificationSettingsRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils.getJwtUsersId;
import static ru.skillbox.diplom.group33.social.service.utils.notification.SettingTypeUtils.getSettingByType;
import static ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil.containsSession;
import static ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil.findSession;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @___(@Lazy))
public class NotificationService {

    private final NotificationSettingsRepository settingsRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSettingsMapper settingsMapper;
    private final AccountService accountService;
    private final NotificationMapper mapper;

    public NotificationSettingsDto getSettings() {
        log.info("NotificationService: getSettings");
        NotificationSettings settings = settingsRepository.findByUserId(getJwtUsersId());
        return settingsMapper.convertToNotificationSettingsDTO(settings);
    }

    public DefaultResponse updateSetting(SettingsRequest requestSettings) {
        log.info("NotificationService: updateSetting {}", requestSettings);
        NotificationSettings settings = settingsRepository.findByUserId(getJwtUsersId());
        settingsRepository.save(getSettingByType(settings, requestSettings));
        return new DefaultResponse(ZonedDateTime.now(),
                new MessageResponse(true));
    }

    public void createSettings(Long accountId) {
        log.info("NotificationService: createSettings for account {}", accountId);
        NotificationSettings settings = new NotificationSettings();
        settings.setUserId(accountId);
        settingsRepository.save(settings);
    }

    public NotificationCountResponse countNotifications() {
        log.info("NotificationService: countNotifications");
        return new NotificationCountResponse(ZonedDateTime.now(),
                new CountResponse(notificationRepository
                        .countAllByReceiverId(getJwtUsersId())));
    }

    public SentNotificationsDto getNotifications() {
        log.info("NotificationService: getNotifications");
        List<NotificationDataResponse> dataSent = notificationRepository
                .findAllByReceiverId(getJwtUsersId()).stream()
                .map(notification -> mapper.convertToDataResponse(notification,
                        accountService.getById(notification.getAuthorId())))
                .collect(Collectors.toList());
        return new SentNotificationsDto(ZonedDateTime.now(), dataSent);
    }

    @KafkaListener(topics = "${topic.names.notify}")
    public NotificationDto addNotification(NotificationInputDto notificationInput) {
        log.info("NotificationService: addNotification {}", notificationInput);
        NotificationDto notificationDto = mapper.convertToNotificationDTO(notificationInput);
        notificationDto.setIsStatusSent(true);
        notificationRepository.save(mapper.convertToNotification(notificationDto));
        sendStreamingNotification(notificationDto);
        return notificationDto;
    }

    public void sendStreamingNotification(NotificationDto notificationDto) {
        Long receiverId = notificationDto.getUserId();
        WebSocketSession session = findSession(receiverId);
        if (!containsSession(session)) return;
        try {
            session.sendMessage(new TextMessage(""));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}