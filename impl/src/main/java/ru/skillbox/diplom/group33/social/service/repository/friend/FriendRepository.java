package ru.skillbox.diplom.group33.social.service.repository.friend;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.dto.friend.StatusCode;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;

@Repository
public interface FriendRepository extends BaseRepository<Friend> {

    List<Friend> findByFromAccountIdAndStatusCode(Long id, StatusCode statusCode);
}
