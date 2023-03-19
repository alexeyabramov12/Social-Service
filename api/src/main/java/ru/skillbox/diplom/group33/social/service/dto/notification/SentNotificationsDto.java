package ru.skillbox.diplom.group33.social.service.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationDataResponse;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто отправленных уведомлений")
public class SentNotificationsDto {

    @Schema(description = "Время получения")
    private ZonedDateTime timeStamp;
    @Schema(description = "Лист отправленных уведомлений")
    private List<NotificationDataResponse> data;
}
