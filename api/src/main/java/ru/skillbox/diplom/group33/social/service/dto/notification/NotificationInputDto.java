package ru.skillbox.diplom.group33.social.service.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто входящее на основе автора и получателя уведомления")
public class NotificationInputDto {

    @Schema(description = "Идентификатор автора уведомления")
    private Long authorId;
    @Schema(description = "Идентификатор получателя уведомления")
    private Long userId;
    @Schema(description = "Тип уведомления", example = "COMMENT_COMMENT")
    private NotificationType notificationType;
    @Schema(description = "Текст уведомления")
    private String content;
}
