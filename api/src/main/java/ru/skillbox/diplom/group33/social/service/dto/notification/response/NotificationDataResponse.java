package ru.skillbox.diplom.group33.social.service.dto.notification.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDataResponse {

    private Long id;
    private AccountDto author;
    private String content;
    private NotificationType notificationType;
    private ZonedDateTime sentTime;
}
