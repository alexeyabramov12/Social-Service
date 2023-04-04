package ru.skillbox.diplom.group33.social.service.dto.dialog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто поиска далога")
public class DialogSearchDto extends BaseSearchDto {

    @Schema(description = "Id автора", example = "3")
    private Long userId;
    @Schema(description = "Id получателя сообщения", example = "5")
    private Long conversationPartnerId;


}
