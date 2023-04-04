package ru.skillbox.diplom.group33.social.service.dto.dialog.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто сообщения")
public class MessageDto extends BaseDto {

    @Schema(description = "Дата и время отправки", example = "1680595355053")
    private Long time;
    @Schema(description = "Id автора сообщения", example = "3")
    private Long authorId;
    @Schema(description = "Id получателя сообщения", example = "5")
    private Long recipientId;
    @Schema(description = "Текст сообщения", example = "Привет")
    private String messageText;
    @Schema(description = "Статус прочтения: SENT, READ - отправлен, прочитан")
    private ReadStatusDto readStates;
    @Schema(description = "Id диалога", example = "2")
    private Long dialogId;
}
