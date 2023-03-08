package ru.skillbox.diplom.group33.social.service.dto.captcha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто для запроса капчи")
public class CaptchaDto {
    @Schema(description = "Идентификатор капчи")
    private String secret;
    @Schema(description = "Изображение капчи закодированое в Base64")
    private String image;

}
