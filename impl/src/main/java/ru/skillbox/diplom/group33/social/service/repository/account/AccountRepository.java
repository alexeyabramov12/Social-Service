package ru.skillbox.diplom.group33.social.service.repository.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;

public interface AccountRepository extends BaseRepository<Account> {

    @Query(value = "SELECT a.id FROM account a WHERE date_part('month', a.birth_date) = :month AND date_part('day', a.birth_date) = :day", nativeQuery = true)
    List<Long> findByBirthDate(@Param("month") int month, @Param("day") int day);
}
