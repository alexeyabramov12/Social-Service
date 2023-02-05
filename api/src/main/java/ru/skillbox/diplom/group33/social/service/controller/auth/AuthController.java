package ru.skillbox.diplom.group33.social.service.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
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
