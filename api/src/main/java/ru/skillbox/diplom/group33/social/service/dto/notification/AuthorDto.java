package ru.skillbox.diplom.group33.social.service.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто автора уведомления")
public class AuthorDto extends BaseDto {

    @Schema(description = "Об авторе")
    private String about;
    @Schema(description = "Статус", example = "FRIEND")
    private StatusCode statusCode;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Дата регистрации")
    private ZonedDateTime regDate;
    @Schema(description = "Дата дня рождения")
    private ZonedDateTime birthDate;
    @Schema(description = "Роль пользователя", example = "USER")
    private String role;
    @Schema(description = "Время когда был последний раз в сети")
    private ZonedDateTime lastOnlineTime;
    @Schema(description = "Находится в сети")
    private Boolean isOnline;
    @Schema(description = "Фото аккаунта")
    private String photo;
    @Schema(description = "Идентификатор фото")
    private String photoId;
    @Schema(description = "Имя фото")
    private String photoName;

}
