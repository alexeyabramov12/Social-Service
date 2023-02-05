package ru.skillbox.diplom.group33.social.service.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostDto extends BaseDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime time;
    private Long authorId;
    private String title;
    private PostType type;
    private String postText;
    private Boolean isBlocked;
    private CommentDto comments;
    private PostTagDto tags;
    private Integer likeAmount;
    private Boolean myLike;
    private String imagePath;
    private ZonedDateTime publishDate;

}
