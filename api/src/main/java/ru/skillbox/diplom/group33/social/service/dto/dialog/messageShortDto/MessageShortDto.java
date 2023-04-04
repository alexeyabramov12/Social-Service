package ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто сообщения краткое")
public class MessageShortDto extends BaseDto {

    @Schema(description = "Дата и время отправки", example = "1680595355053")
    private Long time;
    @Schema(description = "Id автора сообщения", example = "3")
    private Long authorId;
    @Schema(description = "Текст сообщения", example = "Привет")
    private String messageText;
    @Schema(description = "Id получателя сообщения", example = "5")
    private Long recipientId;

}
