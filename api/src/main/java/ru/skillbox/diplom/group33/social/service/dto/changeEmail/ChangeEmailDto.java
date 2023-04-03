package ru.skillbox.diplom.group33.social.service.dto.changeEmail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто для запроса на получение письма для смены электронной почты")
public class ChangeEmailDto {
    @Schema(description = "Электронная почта")
    private String email;
    @Schema(description = "Новая электронная почта")
    private String newEmail;
    @Schema(description = "Капча")
    private String code;
    @Schema(description = "Идентификатор")
    private String temp;
}
