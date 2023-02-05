package ru.skillbox.diplom.group33.social.service.model.post;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.post.PostType;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Column(name = "time", columnDefinition = "timestamp with time zone")
    private ZonedDateTime time;
    @Column(name = "time_changed", columnDefinition = "timestamp with time zone", nullable = false)
    private ZonedDateTime timeChanged;
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "post_type")
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Column(name = "post_text", nullable = false)
    private String postText;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "comments_count", nullable = false)
    private Integer commentsCount;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet();
    @Column(name = "like_amount", nullable = false)
    private Integer likeAmount;
    @Column(name = "my_like")
    private Boolean myLike;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "publish_date", columnDefinition = "timestamp with time zone")
    private ZonedDateTime publishDate;


}
