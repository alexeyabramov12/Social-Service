package ru.skillbox.diplom.group33.social.service.dto.notification.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные ответа количества уведомлений")
public class CountResponse {

    @Schema(description = "Количество уведомлений", example = "4")
    private Long count;
}
