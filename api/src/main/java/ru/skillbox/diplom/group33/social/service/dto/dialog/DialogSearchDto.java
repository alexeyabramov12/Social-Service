package ru.skillbox.diplom.group33.social.service.dto.dialog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

@Data
@Schema(description = "Дто поиска далога")
public class DialogSearchDto extends BaseSearchDto {

    @Schema(description = "Id автора")
    private Long userId;
    @Schema(description = "Id получателя сообщения")
    private Long conversationPartnerId;


}
