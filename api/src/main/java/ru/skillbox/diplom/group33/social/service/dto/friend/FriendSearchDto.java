package ru.skillbox.diplom.group33.social.service.dto.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "Дто поиска друга")
public class FriendSearchDto extends BaseSearchDto {
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Начальная дата для поиска")
    private String birthDateFrom;
    @Schema(description = "Конечная дата для поиска")
    private String birthDateTo;
    @Schema(description = "Город")
    private String city;
    @Schema(description = "Страна")
    private String country;
    @Schema(description = "Начальный возраст")
    private Integer ageTo;
    @Schema(description = "Конечный возраст")
    private Integer ageFrom;
    @Schema(description = "Статус")
    private StatusCode statusCode;
    @Schema(description = "Аккаунт к которому запрос")
    private Long toAccountId;
    @Schema(description = "Аккаунт от которого запрос")
    private Long fromAccountId;
    @Schema(description = "Список id")
    private List<Long> ids;

}
