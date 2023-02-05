package ru.skillbox.diplom.group33.social.service.repository.auth;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.auth.Role;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Role findByName(String name);
}
