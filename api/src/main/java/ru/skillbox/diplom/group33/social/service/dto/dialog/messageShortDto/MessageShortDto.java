package ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто сообщения краткое")
public class MessageShortDto extends BaseDto {

    @Schema(description = "Дата и время отправки", example = "1673667157")
    private Long time;
    @Schema(description = "Id автора сообщения")
    private Long authorId;
    @Schema(description = "Текст сообщения")
    private String messageText;
    @Schema(description = "Id получателя сообщения")
    private Long recipientId;

}
