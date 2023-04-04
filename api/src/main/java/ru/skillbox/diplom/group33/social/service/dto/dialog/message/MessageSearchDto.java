package ru.skillbox.diplom.group33.social.service.dto.dialog.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageSearchDto extends BaseSearchDto {

    @Schema(description = "Id диалога", example = "1")
    private Long dialogId;
    @Schema(description = "Id автора", example = "3")
    private Long authorId;
    @Schema(description = "Id получателя сообщения", example = "5")
    private Long recipientId;
    @Schema(description = "Статус прочтения: SENT, READ - отправлен, прочитан")
    private ReadStatusDto readStatus;

}
