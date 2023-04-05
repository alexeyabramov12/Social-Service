package ru.skillbox.diplom.group33.social.service.repository.changeEmail;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.diplom.group33.social.service.model.changeEmail.ChangeEmail;

import java.util.UUID;

public interface ChangeEmailRepository extends JpaRepository<ChangeEmail, UUID> {
   ChangeEmail findByEmail(String email);

}
