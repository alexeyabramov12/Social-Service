package ru.skillbox.diplom.group33.social.service.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Data
@Schema(description = "Дто пользователя")
public class UserDto extends BaseDto {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Пароль пользователя")
    private String password;
}
