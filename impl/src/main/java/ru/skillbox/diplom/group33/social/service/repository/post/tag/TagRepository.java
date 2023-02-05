package ru.skillbox.diplom.group33.social.service.repository.post.tag;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.post.tag.Tag;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends BaseRepository<Tag> {

    List<Tag> findByNameIn(Set<String> names);
}
