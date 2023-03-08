package ru.skillbox.diplom.group33.social.service.dto.dialog.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто сообщения")
public class MessageDto extends BaseDto {

    @Schema(description = "Дата и время отправки")
    private ZonedDateTime time;
    @Schema(description = "Id автора сообщения")
    private Long authorId;
    @Schema(description = "Id получателя сообщения")
    private Long recipientId;
    @Schema(description = "Текст сообщения")
    private String messageText;
    @Schema(description = "Статус прочтения: SENT, READ - отправлен, прочитан")
    private ReadStatusDto readStates;
    @Schema(description = "Id диалога")
    private Long dialogId;
}
