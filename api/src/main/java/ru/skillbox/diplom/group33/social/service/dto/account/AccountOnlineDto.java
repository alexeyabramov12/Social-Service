package ru.skillbox.diplom.group33.social.service.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дто для отслеживания аккаунта онлайн")
public class AccountOnlineDto {

    @Schema(description = "Идентификатор аккаунта", example = "21313")
    private Long id;
    @Schema(description = "Время последнего появления онлайн", example = "2023-02-26 19:02:26.110138 +00:00")
    private ZonedDateTime lastOnlineTime;
    @Schema(description = "Онлайн?", example = "true")
    private Boolean isOnline;

}
