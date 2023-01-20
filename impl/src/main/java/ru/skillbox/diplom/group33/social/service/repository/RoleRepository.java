package ru.skillbox.diplom.group33.social.service.repository;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
