package ru.skillbox.diplom.group33.social.service.repository.friend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.friend.Friend;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;

@Repository
public interface FriendRepository extends BaseRepository<Friend> {

    @Query(value = "SELECT * FROM friend f WHERE date_part('month', f.birthday) = :month AND date_part('day', f.birthday) = :day AND f.status_code = 'FRIEND'" , nativeQuery = true)
    List<Friend> findByStatusCodeAndBirthDay(@Param("month") int month, @Param("day") int day);
}
