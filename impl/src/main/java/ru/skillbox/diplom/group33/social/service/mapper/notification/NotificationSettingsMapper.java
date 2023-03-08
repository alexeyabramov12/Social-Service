package ru.skillbox.diplom.group33.social.service.mapper.notification;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationSettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.SettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {ZonedDateTime.class, SecurityUtils.class,
        SettingsRequest.class, NotificationType.class, List.class
})
public interface NotificationSettingsMapper {

    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "userId", expression = "java(SecurityUtils.getJwtUsersId())")
    @Mapping(target = "data", expression =
            "java(List.of(new SettingsRequest(settings.getPost(),NotificationType.POST)," +
            " new SettingsRequest(settings.getPostComment(), NotificationType.POST_COMMENT)," +
            " new SettingsRequest(settings.getCommentComment(), NotificationType.COMMENT_COMMENT)," +
            " new SettingsRequest(settings.getMessage(), NotificationType.MESSAGE)," +
            " new SettingsRequest(settings.getFriendRequest(), NotificationType.FRIEND_REQUEST)," +
            " new SettingsRequest(settings.getFriendBirthday(), NotificationType.FRIEND_BIRTHDAY)," +
            " new SettingsRequest(settings.getSendEmailMessage(), NotificationType.SEND_EMAIL_MESSAGE)))")
    NotificationSettingsDto convertToNotificationSettingsDTO(NotificationSettings settings);

    SettingsDto convertToSettingsDTO(NotificationSettings settings);

    NotificationSettings convertToNotificationSettings(SettingsDto settingsDTO);
}
