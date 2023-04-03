package ru.skillbox.diplom.group33.social.service.mapper.changeEmail;

import org.mapstruct.Mapper;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.model.changeEmail.ChangeEmail;

@Mapper(componentModel = "spring")
public interface ChangeEmailMapper {
    ChangeEmail changeEmailToEntity(ChangeEmailDto changeEmailDto);
}
