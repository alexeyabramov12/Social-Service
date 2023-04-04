package ru.skillbox.diplom.group33.social.service.dto.dialog.setStatusMessageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто ответа на вопрос 'Проверить сообщение как прочитанное'")
public class SetStatusMessageReadDto {

    @Schema(description = "Сообщение о выполнении", example = "ok")
    private String message;

}
