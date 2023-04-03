package ru.skillbox.diplom.group33.social.service.model.post.tag;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.model.base.BaseEntity;
import ru.skillbox.diplom.group33.social.service.model.post.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    public Tag(String tag1) {

    }
}
