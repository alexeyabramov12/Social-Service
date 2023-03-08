package ru.skillbox.diplom.group33.social.service.utils.notification;

import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;

public class SettingTypeUtils {

    public static NotificationSettings getSettingByType(NotificationSettings settings, SettingsRequest requestSettings) {
        switch (requestSettings.getNotificationType()) {
            case POST: settings.setPost(requestSettings.getEnable()); break;
            case POST_COMMENT: settings.setPostComment(requestSettings.getEnable()); break;
            case COMMENT_COMMENT: settings.setCommentComment(requestSettings.getEnable()); break;
            case MESSAGE: settings.setMessage(requestSettings.getEnable()); break;
            case FRIEND_REQUEST: settings.setFriendRequest(requestSettings.getEnable()); break;
            case FRIEND_BIRTHDAY: settings.setFriendBirthday(requestSettings.getEnable()); break;
            case SEND_EMAIL_MESSAGE: settings.setSendEmailMessage(requestSettings.getEnable()); break;
        }
        return settings;
    }
}
