package ru.skillbox.diplom.group33.social.service.dto.post.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.time.ZonedDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchDto extends BaseSearchDto {

    private CommentType commentType;
    private ZonedDateTime time;
    private ZonedDateTime timeChanged;
    private Long authorId;
    private Long parentId;
    private String commentText;
    private Long postId	;
    private Boolean isBlocked;
    private Integer likeAmount;
    private Boolean myLike;
    private Integer commentsCount;
    private String imagePath;

}
