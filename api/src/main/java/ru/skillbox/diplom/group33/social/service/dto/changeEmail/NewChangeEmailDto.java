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
@Schema(description = "Дто для новой  электронной почты")
public class NewChangeEmailDto {
    @Schema(description = "Электронная почта")
    private String email;
}
