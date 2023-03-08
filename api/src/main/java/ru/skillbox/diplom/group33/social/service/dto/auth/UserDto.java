package ru.skillbox.diplom.group33.social.service.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
