package ru.skillbox.diplom.group33.social.service.mapper.dialog.message;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.ReadStatusDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto.MessageShortDto;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.ReadStatus;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = ZonedDateTime.class)
public interface MessageMapper {

    MessageDto convertToDto(Message message);

    @Mapping(target = "readStatus", expression = "java(ReadStatus.SEND)")
    Message convertToEntity(MessageDto dto);

    @Mapping(target = "time", expression = "java(message.getTime().toEpochSecond())")
    MessageShortDto convertEntityToShortDto(Message message);

    ReadStatus convertRedStatusDtoToReadStatus(ReadStatusDto dto);

    @Mapping(target = "time", ignore = true)
    MessageShortDto convertDtoToShortDto(MessageDto dto);



}
