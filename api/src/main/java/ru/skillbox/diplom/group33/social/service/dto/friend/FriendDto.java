package ru.skillbox.diplom.group33.social.service.dto.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Schema(description = "Дто друга")
public class FriendDto extends BaseDto {
    @Schema(description = "Статус код")
    private StatusCode statusCode;
    @Schema(description = "Фото профиля")
    private String photo;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Страна")
    private String country;
    @Schema(description = "День рождения")
    private ZonedDateTime birthDate;
    @Schema(description = "В сети/Не в сети")
    private Boolean isOnline;
}
