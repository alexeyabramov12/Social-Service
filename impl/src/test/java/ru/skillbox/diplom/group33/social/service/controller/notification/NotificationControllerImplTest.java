package ru.skillbox.diplom.group33.social.service.controller.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skillbox.diplom.group33.social.service.config.AbstractIntegrationTest;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationSettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.SentNotificationsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.DefaultResponse;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationCountResponse;
import ru.skillbox.diplom.group33.social.service.repository.notification.NotificationRepository;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skillbox.diplom.group33.social.service.config.SourceDataFactory.notification;
import static ru.skillbox.diplom.group33.social.service.config.SourceDataFactory.settingsRequest;
import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.POST;

class NotificationControllerImplTest extends AbstractIntegrationTest {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        String accessToken = getAccessToken();
        notificationRepository.save(notification(tokenProvider.getUserId(accessToken)));
    }

    @Test
    @Order(1)
    void getSettings() {
        ResponseEntity<NotificationSettingsDto> response = getTemplate()
                .getForEntity("/api/v1/notifications/settings",
                        NotificationSettingsDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Objects.requireNonNull(response.getBody()).getData().forEach(settingsRequest ->
        {
            if (settingsRequest.getNotificationType() == POST) {
                assertTrue(settingsRequest.getEnable());
            }
        });
    }

    @Test
    @Order(2)
    void updateSettings() {
        ResponseEntity<DefaultResponse> response = getTemplate()
                .exchange("/api/v1/notifications/settings", HttpMethod.PUT,
                        new HttpEntity<>(settingsRequest()), DefaultResponse.class);
        assertTrue(Objects.requireNonNull(response.getBody()).getStatus().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(3)
    void getNotifications() {
        ResponseEntity<SentNotificationsDto> response = getTemplate()
                .getForEntity("/api/v1/notifications",
                SentNotificationsDto.class);
        assertNotNull(Objects.requireNonNull(response.getBody()).getData());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void getCountNotifications() {
        Long count = notificationRepository.count();
        ResponseEntity<NotificationCountResponse> response = getTemplate()
                .getForEntity("/api/v1/notifications/count", NotificationCountResponse.class);
        assertEquals(Objects.requireNonNull(response.getBody()).getData().getCount(), count);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}