package ru.skillbox.diplom.group33.social.service.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class UserDto {
    @Schema(description = "Фамилия")
    private String firstName;
    @Schema(description = "Имя")
    private String lastName;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Пароль пользователя")
    private String password;
}
