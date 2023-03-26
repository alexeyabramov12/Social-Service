package ru.skillbox.diplom.group33.social.service.dto.dialog.setStatusMessageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто ответа на вопрос 'Проверить сообщение как прочитанное'")
public class SetStatusMessageReadDto {

    @Schema(description = "Сообщение о выполнении", example = "ok")
    private String message;

}
