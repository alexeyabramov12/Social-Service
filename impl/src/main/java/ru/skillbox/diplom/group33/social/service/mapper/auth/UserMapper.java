package ru.skillbox.diplom.group33.social.service.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.UserDto;
import ru.skillbox.diplom.group33.social.service.model.auth.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    UserDto registrationToUserDto(RegistrationDto registrationDto);

    RegistrationDto userDtoToRegistration(UserDto userDto);

    @Mapping(target = "password", expression = "java(bc.encode(dto.getPassword1()))")
    User registrationToUser(RegistrationDto dto);

    UserDto convertToDto(User user);
}
