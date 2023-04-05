package ru.skillbox.diplom.group33.social.service.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.auth.RegistrationDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.NewChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.NewPasswordDto;
import ru.skillbox.diplom.group33.social.service.dto.passwordRecovery.PasswordRecoveryDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name = "Система авторизации пользователя")
@RequestMapping(PathConstant.URL + "auth/")
public interface AuthController {
    @PostMapping("login")
    @Operation(summary = "Вход в соц. сеть")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<AuthenticateResponseDto> login(AuthenticateDto authenticateDto);

    @GetMapping("logout")
    @Operation(summary = "Выход из соц. сети")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный выход", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void logout();

    @PostMapping("register")
    @Operation(summary = "Регистрация в соц. сети")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void register(@RequestBody RegistrationDto registrationDto);

    @GetMapping("captcha")
    @Operation(summary = "Капча")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное прохождение капчи", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<CaptchaDto> captcha();

    @PostMapping("change-email-link")
    @Operation(summary = "Смена электронной почты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Письмо о смени электронной почты отправлино", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void changeEmail(@RequestBody ChangeEmailDto changeEmailDto);

    @PostMapping("change-password-link")
    @Operation(summary = "Смена пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Письмо о смене пароля отправлено", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void changePassword(@RequestBody PasswordRecoveryDto passwordRecoveryDto);

    @PostMapping(value = "password/recovery/{linkId}")
    @Operation(summary = "Установка нового пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная смена пароля", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void setPassword(@PathVariable String linkId, @RequestBody NewPasswordDto newPasswordDto);

    @PutMapping(value = "email")
    @Operation(summary = "Отправка письма на новую электронную почту для подтверждения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "На ваш новый e-mail отправлено письмо для подтверждения", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void emailSuccess(@RequestBody NewChangeEmailDto newChangeEmailDto);

    @PutMapping(value = "confirm-email")
    @Operation(summary = "Установка новой электронной почты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новая электронная почта установлена", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void setEmail();
}
