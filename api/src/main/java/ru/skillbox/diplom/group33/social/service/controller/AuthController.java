package ru.skillbox.diplom.group33.social.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.auth.dto.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@RequestMapping(PathConstant.URL + "auth/")
public interface AuthController {
    @PostMapping("login")
    ResponseEntity<AuthenticateResponseDto> login(AuthenticateDto authenticateDto);
    @GetMapping("api/v1/logout")
    void logout();
    @PostMapping("register")
    void register(@RequestBody RegistrationDto registrationDto);

    @GetMapping("captcha")
    ResponseEntity<CaptchaDto> captcha();
}
