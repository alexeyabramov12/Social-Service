package ru.skillbox.diplom.group33.social.service.mapper.notification;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationDataResponse;
import ru.skillbox.diplom.group33.social.service.model.notification.Notification;

import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {
        ZonedDateTime.class, List.class,
        AccountDto.class, NotificationDataResponse.class,
        Notification.class})
public interface NotificationMapper {

    @Mapping(target = "time", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "isStatusSent", ignore = true)
    NotificationDto convertToNotificationDTO(NotificationInputDto notificationInput);

    @Mapping(target = "isStatusSent", ignore = true)
    @Mapping(target = "userId", source = "receiverId")
    NotificationDto convertToNotificationDTO(Notification notification);

    @Mapping(target = "receiverId", source = "userId")
    Notification convertToNotification(NotificationDto notificationDto);

    @Mapping(target = "id", source = "notification.id")
    @Mapping(target = "author", source = "account")
    @Mapping(target = "sentTime", source = "notification.time")
    NotificationDataResponse convertToDataResponse(Notification notification, AccountDto account);

//    @Mapping(target = "type", expression = "java('NOTIFICATION')")
//    @Mapping(target = "accountId", source = "notificationDto.userId")
//    @Mapping(target = "data", source = "notificationDto")
//    StreamingMessageDto<NotificationDto> convertToStreamingMessageDto(NotificationDto notificationDto);
}
