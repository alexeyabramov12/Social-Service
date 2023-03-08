package ru.skillbox.diplom.group33.social.service.dto.dialog.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSearchDto extends BaseSearchDto {

    @Schema(description = "Id диалога")
    private Long dialogId;
    @Schema(description = "Id автора")
    private Long authorId;
    @Schema(description = "Id получателя сообщения")
    private Long recipientId;
    @Schema(description = "Статус прочтения: SENT, READ - отправлен, прочитан")
    private ReadStatusDto readStatus;

}
