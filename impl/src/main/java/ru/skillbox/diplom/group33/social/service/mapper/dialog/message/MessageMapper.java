package ru.skillbox.diplom.group33.social.service.mapper.dialog.message;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.ReadStatusDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto.MessageShortDto;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.ReadStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class, Instant.class, ZoneId.class})
public interface MessageMapper {

    @Mapping(target = "time", expression = "java(message.getTime().toEpochSecond())")
    MessageDto convertToDto(Message message);

    @Mapping(target = "readStatus", expression = "java(ReadStatus.SEND)")
    @Mapping(target = "time", expression = "java(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dto.getTime()), ZoneId.systemDefault()))")
    Message convertToEntity(MessageDto dto);

    @Mapping(target = "time", expression = "java(message.getTime().toEpochSecond())")
    MessageShortDto convertEntityToShortDto(Message message);

    ReadStatus convertRedStatusDtoToReadStatus(ReadStatusDto dto);

    MessageShortDto convertDtoToShortDto(MessageDto dto);


}
