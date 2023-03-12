package ru.skillbox.diplom.group33.social.service.model.post.comment;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.post.comment.CommentType;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type")
    private CommentType commentType;
    @Column(name = "time", columnDefinition = "timestamp with time zone")
    private ZonedDateTime time;
    @Column(name = "time_changed", columnDefinition = "timestamp with time zone", nullable = false)
    private ZonedDateTime timeChanged;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "comment_text", nullable = false)
    private String commentText;
    @Column(name = "post_id", nullable = false)
    private Long postId;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "like_amount")
    private Integer likeAmount;
    @Column(name = "my_like")
    private Boolean myLike;
    @Column(name = "comment_count")
    private Integer commentsCount;
    @Column(name = "image_path")
    private String imagePath;

}
