package ru.skillbox.diplom.group33.social.service.repository.passwordRecovery;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.diplom.group33.social.service.model.passwordRecovery.PasswordRecovery;

import java.util.UUID;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, UUID> {
    PasswordRecovery findByEmail(String email);
}
