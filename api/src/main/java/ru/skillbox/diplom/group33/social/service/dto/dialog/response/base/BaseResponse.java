package ru.skillbox.diplom.group33.social.service.dto.dialog.response.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Базовый Респонс")
public class BaseResponse {

    @Schema(description = "Ошибка по запросу", example = "Неверный запрос")
    private String error;
    @Schema(description = "Описание ошибки", example = "Неверный код авторизации")
    private String errorDescription;
    @Schema(description = "Метка времени", example = "1644234125")
    private Long timeStamp;
}
