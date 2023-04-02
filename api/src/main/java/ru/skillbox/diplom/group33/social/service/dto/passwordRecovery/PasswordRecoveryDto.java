package ru.skillbox.diplom.group33.social.service.dto.passwordRecovery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто для запроса на получение письма для востановления пароля")
public class PasswordRecoveryDto {
    @Schema(description = "Идентификатор")
    private String secretLinkId;
    @Schema(description = "Электронная почта")
    private String email;
}
