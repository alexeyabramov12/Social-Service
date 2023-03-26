package ru.skillbox.diplom.group33.social.service.dto.dialog.unreadCountDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто количества непрочитанных сообщений диалога")
public class UnreadCountDto {

    @Schema(description = "количество непрочитнных сообщений в диалоге")
    private Long count;

}
