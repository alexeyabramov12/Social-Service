package ru.skillbox.diplom.group33.social.service.controller.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.NewChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.NewPasswordDto;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.PasswordRecoveryDto;
import ru.skillbox.diplom.group33.social.service.service.auth.AuthService;
import ru.skillbox.diplom.group33.social.service.service.captcha.CaptchaService;
import ru.skillbox.diplom.group33.social.service.service.changeEmail.ChangeEmailService;
import ru.skillbox.diplom.group33.social.service.service.passwordRecovery.PasswordRecoveryService;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    private final CaptchaService captchaService;
    private final PasswordRecoveryService passwordRecoveryService;
    private final ChangeEmailService changeEmailService;


    @Override
    public ResponseEntity<AuthenticateResponseDto> login(@RequestBody AuthenticateDto authenticateDto) {
        log.info("login controller entered");
        final AuthenticateResponseDto response = authService.login(authenticateDto);
        return ResponseEntity.ok(response);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public void register(RegistrationDto registrationDto) {
        log.info("Registration controller entered");
        authService.register(registrationDto);
    }

    @Override
    public ResponseEntity<CaptchaDto> captcha() {
        log.info("In AuthControllerImpl captcha");
        return ResponseEntity.status(HttpStatus.OK).body(captchaService.getCaptcha());
    }

    @Override
    public void changeEmail(ChangeEmailDto changeEmailDto) {
        changeEmailService.getChangeEmailMail(changeEmailDto);
    }

    @Override
    public void changePassword(PasswordRecoveryDto passwordRecoveryDto) {
        passwordRecoveryService.getPasswordRecoveryMail(passwordRecoveryDto);
    }

    @Override
    public void setPassword(String linkId, NewPasswordDto newPasswordDto) {
        passwordRecoveryService.setPassword(linkId, newPasswordDto);
    }

    @Override
    public void emailSuccess(NewChangeEmailDto newChangeEmailDto) {
        changeEmailService.emailSuccess(newChangeEmailDto);

    }

    @Override
    public void setEmail() {
        changeEmailService.setEmail();
    }


}
