package ru.skillbox.diplom.group33.social.service.dto.post.comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class CommentDto extends BaseDto {

    private CommentType commentType;
    private ZonedDateTime time;
    private ZonedDateTime timeChanged;
    private Long authorId;
    private Long parentId;
    private String commentText;
    private Long postId;
    private Boolean isBlocked;
    private Integer likeAmount;
    private Boolean myLike;
    private Integer commentsCount;
    private String imagePath;
}
