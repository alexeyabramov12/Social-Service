package ru.skillbox.diplom.group33.social.service.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInputDto {

    private Long authorId;
    private Long userId;
    private NotificationType notificationType;
    private String content;
}
