package ru.skillbox.diplom.group33.social.service.dto.notification.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ получения количества уведомлений")
public class NotificationCountResponse {

    @Schema(description = "Время ответа")
    private ZonedDateTime timestamp;
    @Schema(description = "Данные о количестве уведомлений", example = "{count: 4}")
    private CountResponse data;
}
