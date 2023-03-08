package ru.skillbox.diplom.group33.social.service.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто настроек уведомлений")
public class SettingsDto extends BaseDto {

    @Schema(description = "Идентификатор пользователя")
    private Long userId;
    @Schema(description = "О новом запросе в друзья")
    private Boolean friendRequest;
    @Schema(description = "О дне рождения друга")
    private Boolean friendBirthday;
    @Schema(description = "О новом комментарии к посту")
    private Boolean postComment;
    @Schema(description = "О новом комментарии к комментарию")
    private Boolean commentComment;
    @Schema(description = "О новом посте")
    private Boolean post;
    @Schema(description = "О новом сообщении")
    private Boolean message;
    @Schema(description = "Уведомлять по телефону")
    private Boolean sendPhoneMessage;
    @Schema(description = "Уведомлять по email")
    private Boolean sendEmailMessage;
}
