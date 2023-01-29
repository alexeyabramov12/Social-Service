package ru.skillbox.diplom.group33.social.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.skillbox.diplom.group33.social.service.dto.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.UserDto;
import ru.skillbox.diplom.group33.social.service.model.User;

@Mapper(componentModel = "spring")
public interface SimpleMapper {
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    UserDto registrationToUserDto(RegistrationDto registrationDto);

    RegistrationDto userDtoToRegistration(UserDto userDto);

    @Mapping(target = "password", expression = "java(bc.encode(dto.getPassword1()))")
    User registrationToUser(RegistrationDto dto);

}
