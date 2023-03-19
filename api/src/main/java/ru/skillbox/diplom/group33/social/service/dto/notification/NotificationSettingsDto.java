package ru.skillbox.diplom.group33.social.service.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто настроек уведомлений")
public class NotificationSettingsDto {

    @Schema(description = "Время изменения")
    private ZonedDateTime time;
    @Schema(description = "Данные с типами настроек")
    private List<SettingsRequest> data;
    @Schema(description = "Идентификатор пользователя")
    @JsonProperty(value = "user_id")
    private Long userId;
}
