package ru.skillbox.diplom.group33.social.service.service.captcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.auth.dto.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.repository.captcha.CaptchaRepository;
import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;


@Service
@Slf4j
public class CaptchaService {

    private final CaptchaRepository captchaRepository;

    private final String IMAGE_FORMAT = "png";

    @Autowired
    public CaptchaService(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    public CaptchaDto getCaptcha() {
        Captcha captcha = createCaptcha();
        captcha.setTime(ZonedDateTime.now());
        captchaRepository.save(captcha);

        CaptchaDto captchaDto = new CaptchaDto();
        captchaDto.setSecret(captcha.getSecret().toString());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), IMAGE_FORMAT, bos);
            String prefix = "data:image/" + IMAGE_FORMAT + ";base64, ";
            captchaDto.setImage(prefix + Base64.getEncoder().encodeToString(bos.toByteArray()));
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return captchaDto;
    }

    private Captcha createCaptcha() {
        return new CaptchaBuilder()
                .createImage(121, 45)
                .setText(20, 30, 30, 4)
                .setLines(10)
                .build();
    }

    public boolean passCaptcha(RegistrationDto registrationDto) {
        Captcha captchaOrigin = captchaRepository.findById(UUID.fromString(registrationDto.getToken())).orElseThrow(RuntimeException::new);
        log.info("pass captcha: {}", captchaOrigin.getCode().equals(registrationDto.getCode()));
        return captchaOrigin.getCode().equals(registrationDto.getCode());
    }

}
