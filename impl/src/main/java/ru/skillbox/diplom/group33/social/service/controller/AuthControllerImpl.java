package ru.skillbox.diplom.group33.social.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.service.AuthService;
import ru.skillbox.diplom.group33.social.service.service.captcha.CaptchaService;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    private final CaptchaService captchaService;
    @Override
    public ResponseEntity<AuthenticateResponseDto> login(@RequestBody AuthenticateDto authenticateDto) {
        log.info("login");
        final AuthenticateResponseDto response = authService.login(authenticateDto);
        return ResponseEntity.ok(response);
    }
    @Override
    public void logout() {
    }
    @Override
    @ResponseStatus(HttpStatus.OK)
    public void register(RegistrationDto registrationDto) {
        log.error("register");
        authService.register(registrationDto);
    }

    @Override
    public ResponseEntity<CaptchaDto> captcha() {
        return ResponseEntity.status(HttpStatus.OK).body(captchaService.getCaptcha());
    }
}
