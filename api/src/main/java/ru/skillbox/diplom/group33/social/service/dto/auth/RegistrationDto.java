package ru.skillbox.diplom.group33.social.service.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "Дто регистрации пользователя")
public class RegistrationDto {
    @Schema(description = "Электронная почта")
    private String email;
    @Schema(description = "Пароль")
    public String password1;
    @Schema(description = "Подтверждение пароля")
    private String password2;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Код капчи")
    private String code;
    @Schema(description = "Токен Авторизации")
    private String token;
}
