package ru.skillbox.diplom.group33.social.service.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Дто ответа аутентификации")
public class AuthenticateResponseDto {
    @Schema(description = "Токен доступа")
    private String accessToken;
    @Schema(description = "Обновленный токен")
    private String refreshToken;
}
