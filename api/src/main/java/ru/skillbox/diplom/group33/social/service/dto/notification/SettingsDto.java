package ru.skillbox.diplom.group33.social.service.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDto extends BaseDto {

    private Long userId;
    private Boolean friendRequest;
    private Boolean friendBirthday;
    private Boolean postComment;
    private Boolean commentComment;
    private Boolean post;
    private Boolean message;
    private Boolean sendPhoneMessage;
    private Boolean sendEmailMessage;
}
