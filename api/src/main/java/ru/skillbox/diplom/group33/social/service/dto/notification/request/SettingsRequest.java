package ru.skillbox.diplom.group33.social.service.dto.notification.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(description = "Запрос на изменение настройки по типу уведомления")
public class SettingsRequest {

    @Schema(description = "Включить или выключить")
    private Boolean enable;
    @Schema(description = "Тип уведомления", example = "POST")
    @JsonProperty(value = "notification_type")
    private NotificationType notificationType;
}
