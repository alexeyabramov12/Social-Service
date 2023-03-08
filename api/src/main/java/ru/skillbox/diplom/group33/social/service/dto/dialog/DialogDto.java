package ru.skillbox.diplom.group33.social.service.dto.dialog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто диалога")
public class DialogDto extends BaseDto {

    @Schema(description = "Колличесто непрочитанных сообщений деалога", example = "10")
    private Long unreadCount;
    @Schema(description = "Собеседник")
    private AccountDto conversationPartner;
    @Schema(description = "Посленднее сообщение диалога")
    private MessageDto lastMessage;

}
