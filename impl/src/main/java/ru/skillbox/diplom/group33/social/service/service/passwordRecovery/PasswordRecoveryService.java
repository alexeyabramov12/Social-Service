package ru.skillbox.diplom.group33.social.service.service.passwordRecovery;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.mail.MailConfig;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.NewPasswordDto;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.PasswordRecoveryDto;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.model.passwordRecovery.PasswordRecovery;
import ru.skillbox.diplom.group33.social.service.repository.auth.UserRepository;
import ru.skillbox.diplom.group33.social.service.repository.passwordRecovery.PasswordRecoveryRepository;
import ru.skillbox.diplom.group33.social.service.service.mail.MailSender;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordRecoveryService {
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final MailSender mailSender;
    private final MailConfig mailConfig;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public void setPassword(String linkId, NewPasswordDto newPasswordDto) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepository
                .findById(UUID.fromString(linkId)).orElseThrow(NullPointerException::new);
        User user = userRepository.findByEmail(passwordRecovery.getEmail())
                .orElseThrow(NullPointerException::new);
        user.setPassword(encoder.encode(newPasswordDto.getPassword()));
        userRepository.save(user);
        passwordRecoveryRepository.deleteAllByIdInBatch(List.of(UUID.fromString(linkId)));
    }

    public void getPasswordRecoveryMail(PasswordRecoveryDto passwordRecoveryDto) {
        PasswordRecoveryDto passwordRecoveryDtoFromDb = getPasswordRecoveryDto(passwordRecoveryDto.getEmail());
        mailSender.send(passwordRecoveryDtoFromDb.getEmail(), "смена пароля", "Чтобы придумать новый пароль, перейдите по этой ссылке: "
                + "http://" + mailConfig.getUrl() + "/change-password/" + passwordRecoveryDtoFromDb.getSecretLinkId());
    }

    public PasswordRecoveryDto getPasswordRecoveryDto(String email) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepository.findByEmail(email);
        if (passwordRecovery == null) {
            passwordRecovery = new PasswordRecovery();
            passwordRecovery.setEmail(email);
        }
        PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto();
        passwordRecoveryDto.setSecretLinkId(passwordRecoveryRepository.save(passwordRecovery).getSecretLinkId().toString());
        passwordRecoveryDto.setEmail(passwordRecovery.getEmail());
        return passwordRecoveryDto;

    }

}

