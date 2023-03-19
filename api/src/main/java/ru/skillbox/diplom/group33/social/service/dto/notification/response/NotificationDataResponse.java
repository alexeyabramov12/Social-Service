package ru.skillbox.diplom.group33.social.service.dto.notification.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.AuthorDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные отправленного уведомления")
public class NotificationDataResponse {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Автор уведомления")
    private AuthorDto author;
    @Schema(description = "Текст уведомления")
    private String content;
    @Schema(description = "Тип уведомления")
    private NotificationType notificationType;
    @Schema(description = "Время отправки")
    private ZonedDateTime sentTime;
}
