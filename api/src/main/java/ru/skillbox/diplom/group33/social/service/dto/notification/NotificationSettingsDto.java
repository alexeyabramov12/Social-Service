package ru.skillbox.diplom.group33.social.service.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NotificationSettingsDto {

    private ZonedDateTime time;
    private List<SettingsRequest> data;
    @JsonProperty(value = "user_id")
    private Long userId;
}
