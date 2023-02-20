package ru.skillbox.diplom.group33.social.service.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto extends BaseDto {

    @Schema(description = "Дата и время с учетом локации", example = "07 Mar 2023 21:20:25 Moscow/Europe")
    private ZonedDateTime time;
    @Schema(description = "Id автора", example = "345")
    private Long authorId;
    @Schema(description = "Id целевого получателя", example = "232")
    private Long userId;
    @Schema(description = "Часть контента", example = "Заголовок поста")
    private String content;
    @Schema(description = "Тип уведомления", example = "POST")
    private NotificationType notificationType;
    @Schema(description = "Статус отправлен", example = "true")
    private Boolean isStatusSent;
}
