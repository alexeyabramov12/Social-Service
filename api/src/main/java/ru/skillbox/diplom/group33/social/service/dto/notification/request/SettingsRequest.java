package ru.skillbox.diplom.group33.social.service.dto.notification.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsRequest {

    private Boolean enable;
    @JsonProperty(value = "notification_type")
    private NotificationType notificationType;
}
