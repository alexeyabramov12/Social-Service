package ru.skillbox.diplom.group33.social.service.service.changeEmail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.diplom.group33.social.service.config.mail.MailConfig;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.NewChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.mapper.changeEmail.ChangeEmailMapper;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.model.changeEmail.ChangeEmail;
import ru.skillbox.diplom.group33.social.service.repository.auth.UserRepository;
import ru.skillbox.diplom.group33.social.service.repository.changeEmail.ChangeEmailRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.captcha.CaptchaService;
import ru.skillbox.diplom.group33.social.service.service.mail.MailSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChangeEmailService {
    private final AccountService accountService;
    private final MailSender mailSender;
    private final MailConfig mailConfig;
    private final UserRepository userRepository;
    private final ChangeEmailRepository changeEmailRepository;
    private final ChangeEmailMapper changeEmailMapper;
    private final CaptchaService captchaService;
    public void getChangeEmailMail(ChangeEmailDto changeEmailDto) {
        mailSender.send(changeEmailDto.getEmail(), "смена электроной почты", "Чтобы изменить электроную почту, перейдите по этой ссылке: "
                + "http://" + mailConfig.getUrl() + "/shift-email/");
    }
    public void enteringNewEmailAndCheckCaptcha(ChangeEmailDto changeEmailDto) {
        if (!captchaService.passCaptcha(changeEmailDto)) {
            log.warn("In AuthService register: user failed captcha");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    public ChangeEmailDto getChangeEmailDto(String email) {
        AccountDto accountDto=accountService.getAccount();
        ChangeEmailDto changeEmailDto = new ChangeEmailDto();
        changeEmailDto.setEmail(accountDto.getEmail());
        changeEmailDto.setNewEmail(email);
        changeEmailRepository.save(changeEmailMapper.changeEmailToEntity(changeEmailDto));
        return changeEmailDto;

    }
    public void setEmail(){
        AccountDto accountDto=accountService.getAccount();
        User user = userRepository.findByEmail(accountDto.getEmail())
                .orElseThrow(NullPointerException::new);
        ChangeEmail changeEmail=changeEmailRepository.findByEmail(accountDto.getEmail());
        user.setEmail(changeEmail.getNewEmail());
        userRepository.save(user);
    }
    public void emailSuccess(NewChangeEmailDto newChangeEmailDto){
        ChangeEmailDto changeEmailDtoFromDb=getChangeEmailDto(newChangeEmailDto.getEmail());
        mailSender.send(changeEmailDtoFromDb.getNewEmail(), "Подтверждение электронной почты", "Чтобы подтвердить электронную почту, перейдите по этой ссылке: "
                + "http://" + mailConfig.getUrl() + "/shift-email-confirm/");
    }
}
