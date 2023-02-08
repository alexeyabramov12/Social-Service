package ru.skillbox.diplom.group33.social.service.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

@Getter
@Setter
@Schema(description = "Дто поиска поста")
public class PostSearchDto extends BaseSearchDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Время с учетом временой зонны")
    private ZonedDateTime time;
    @Schema(description = "Идентификатор автора")
    private Long authorId;
    @Schema(description = "Заголовок поста")
    private String title;
    @Schema(description = "Тип потса")
    private PostType type;
    @Schema(description = "Текст потса")
    private String postText;
    @Schema(description = "Блокировка потса")
    private Boolean isBlocked;
    @Schema(description = "Комментарии")
    private CommentDto comments;
    @Schema(description = "Теги")
    private PostTagDto tags;
    @Schema(description = "Количество лайков")
    private Integer likeAmount;
    @Schema(description = "Мой лайк")
    private Boolean myLike;
    @Schema(description = "Путь к изображению  поста")
    private String imagePath;
    @Schema(description = "Дата публикации поста")
    private ZonedDateTime publishDate;

}
