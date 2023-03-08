package ru.skillbox.diplom.group33.social.service.mapper.notification;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.AuthorDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationDataResponse;
import ru.skillbox.diplom.group33.social.service.dto.streaming.StreamingMessageDto;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.notification.Notification;
import ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {
        ZonedDateTime.class, List.class,
        AccountDto.class, NotificationDataResponse.class,
        Notification.class, SecurityUtils.class,
        NotificationDto.class})
public interface NotificationMapper {

    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "receiverId", source = "notificationInput.userId")
    Notification convertToNotification(NotificationInputDto notificationInput);


    @Mapping(target = "userId", source = "receiverId")
    @Mapping(target = "isStatusSent", ignore = true)
    NotificationDto convertToNotificationDto(Notification notification);

    @Mapping(target = "id", source = "notification.id")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "sentTime", source = "notification.time")
    NotificationDataResponse convertToDataResponse(Notification notification, AuthorDto author);

    @Mapping(target = "statusCode", constant = "NONE")
    @Mapping(target = "role", constant = "USER")
    AuthorDto convertToAuthorDto(Account account);

    @Mapping(target = "type", constant = "NOTIFICATION")
    @Mapping(target = "accountId", source = "notificationDto.userId")
    @Mapping(target = "data", source = "notificationDto")
    StreamingMessageDto<NotificationDto> convertToStreaming(NotificationDto notificationDto);
}
