package ru.skillbox.diplom.group33.social.service.repository.notification;

import ru.skillbox.diplom.group33.social.service.model.notification.NotificationSettings;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

public interface NotificationSettingsRepository extends BaseRepository<NotificationSettings> {
    NotificationSettings findByUserId(Long jwtUsersId);
}
