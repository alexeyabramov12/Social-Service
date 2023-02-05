package ru.skillbox.diplom.group33.social.service.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Дто поста")
public class PostDto extends BaseDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Время с учетом временной зонны")
    private ZonedDateTime time;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(description = "Время иземения поста")
    private ZonedDateTime timeChanged;
    @Schema(description = "Идентификатор автора")
    private Long authorId;
    @Schema(description = "Заголовок поста")
    private String title;
    @Schema(description = "Тип поста")
    private PostType postType;
    @Schema(description = "Текст поста")
    private String postText;
    @Schema(description = "Блокировка поста")
    private Boolean isBlocked;
    @Schema(description = "Комментарии")
    private Integer commentsCount;
    @Schema(description = "Теги")
    private Set<String> tags = new HashSet<>();
    @Schema(description = "Количество лайков")
    private Integer likeAmount;
    @Schema(description = "?")
    private Boolean myLike;
    @Schema(description = "Путь к изображению  поста")
    private String imagePath;
    @Schema(description = "Время публикации поста")
    private ZonedDateTime publishDate;

}
