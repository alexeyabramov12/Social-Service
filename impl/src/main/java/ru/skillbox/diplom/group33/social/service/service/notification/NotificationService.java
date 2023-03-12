package ru.skillbox.diplom.group33.social.service.service.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.skillbox.diplom.group33.social.service.model.notification.Notification;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.repository.notification.NotificationRepository;
import ru.skillbox.diplom.group33.social.service.repository.notification.NotificationSettingsRepository;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.notification.SettingTypeUtils.setSettingByType;
import static ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext;
import static ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil.findSession;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final AccountRepository accountRepository;
    private final NotificationSettingsRepository settingsRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationSettingsMapper settingsMapper;
    private final NotificationMapper mapper;
    private final ObjectMapper objectMapper;

    public NotificationSettingsDto getSettings() {
        log.info("NotificationService: getSettings");
        NotificationSettings settings = settingsRepository.findByUserId(getJwtUserIdFromSecurityContext());
        return settingsMapper.convertToNotificationSettingsDto(settings);
    }

    public NotificationSettings getSettings(Long accountId) {
        log.info("NotificationService: getSettings {}", accountId);
        return settingsRepository.findByUserId(accountId);
    }

    public DefaultResponse updateSetting(SettingsRequest requestSettings) {
        log.info("NotificationService: updateSetting {}", requestSettings);
        NotificationSettings settings = settingsRepository.findByUserId(getJwtUserIdFromSecurityContext());
        settingsRepository.save(setSettingByType(settings, requestSettings));
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
        Long count = notificationRepository.countAllByReceiverIdAndIsDeleted(getJwtUserIdFromSecurityContext(), false);
        setDeletedNotifications();
        return new NotificationCountResponse(ZonedDateTime.now(), new CountResponse(count));
    }

    public SentNotificationsDto getNotifications() {
        log.info("NotificationService: getNotifications");
        List<NotificationDataResponse> dataSent = notificationRepository
                .findAllByReceiverId(getJwtUserIdFromSecurityContext()).stream()
                .map(notification -> mapper.convertToDataResponse(notification,
                     mapper.convertToAuthorDto(accountRepository
                           .findById(notification.getAuthorId()).orElseThrow())))
                .collect(Collectors.toList());
        return new SentNotificationsDto(ZonedDateTime.now(), dataSent);
    }

    @KafkaListener(topics = "${topic.names.notify}")
    public void addNotification(NotificationInputDto notificationInput) {
        log.info("Consumed NotificationService: addNotification {}", notificationInput);
        Notification notification = mapper.convertToNotification(notificationInput);
        NotificationDto notificationDto =  mapper.convertToNotificationDto(
                notificationRepository.save(notification));
        sendStreamingNotification(notificationDto);
    }

    public void sendStreamingNotification(NotificationDto notificationDto) {
        WebSocketSession session = findSession(notificationDto.getUserId());
        if (session != null) {
            notificationDto.setIsStatusSent(true);
            log.info("NotificationService: sendStreamingNotification {}", session);
            try {
                session.sendMessage(new TextMessage(objectMapper
                        .writeValueAsString(mapper.convertToStreaming(notificationDto))));
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }

    public void setDeletedNotifications() {
        notificationRepository.saveAll(notificationRepository
                .findAllByReceiverIdAndIsDeleted(getJwtUserIdFromSecurityContext(), false).stream()
                .peek(notification -> notification.setIsDeleted(true))
                .collect(Collectors.toList()));
    }
}