package ru.skillbox.diplom.group33.social.service.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Дто для аутентификации")
public class AuthenticateDto {
    @Schema(description = "Почта")
    private String email;
    @Schema(description = "Пароль")
    private String password;
}
