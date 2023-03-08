package ru.skillbox.diplom.group33.social.service.dto.notification;

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
public class SentNotificationsDto {

    private ZonedDateTime timeStamp;
    private List<NotificationDataResponse> data;
}
