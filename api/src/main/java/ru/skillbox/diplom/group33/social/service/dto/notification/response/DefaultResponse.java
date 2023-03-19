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
@Schema(description = "Ответ на изменение натроек уведомлений")
public class DefaultResponse {

    @Schema(description = "Время ответа")
    private ZonedDateTime time;
    @Schema(description = "Статус ответа", example = "{message: true}")
    private MessageResponse status;
}
