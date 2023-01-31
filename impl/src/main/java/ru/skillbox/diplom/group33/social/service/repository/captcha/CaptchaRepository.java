package ru.skillbox.diplom.group33.social.service.repository.captcha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;

import java.util.UUID;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, UUID> {

}
