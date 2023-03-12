package ru.skillbox.diplom.group33.social.service.repository.post.like;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.dto.post.like.LikeType;
import ru.skillbox.diplom.group33.social.service.model.post.like.Like;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.Optional;

@Repository
public interface LikeRepository extends BaseRepository<Like> {

    Optional<Like> findByAuthorIdAndTypeAndItemId (Long authorId, LikeType likeType, Long itemId);
}