package ru.skillbox.diplom.group33.social.service.dto.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

@Getter
@Setter
@ToString
@Schema(description = "Дто рекомендации друзей")
public class RecommendationFriendsDto extends BaseDto {
    @Schema(description = "Фото профиля")
    private String photo;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;


}
