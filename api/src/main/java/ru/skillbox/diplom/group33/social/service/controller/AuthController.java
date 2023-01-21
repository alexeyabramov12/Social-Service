package ru.skillbox.diplom.group33.social.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.captcha.dto.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.RegistrationDto;

public interface AuthController {
    @PostMapping("api/v1/auth/login")
    ResponseEntity<AuthenticateResponseDto> login(AuthenticateDto authenticateDto);
    @GetMapping("api/v1/logout")
    void logout();
    @PostMapping("api/v1/auth/register")
    void register(@RequestBody RegistrationDto registrationDto);

    @GetMapping("api/v1/auth/captcha")
    ResponseEntity<CaptchaDto> captcha();
}
