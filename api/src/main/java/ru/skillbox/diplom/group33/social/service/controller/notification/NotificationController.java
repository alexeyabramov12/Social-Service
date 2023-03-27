package ru.skillbox.diplom.group33.social.service.controller.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationSettingsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.SentNotificationsDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.request.SettingsRequest;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.DefaultResponse;
import ru.skillbox.diplom.group33.social.service.dto.notification.response.NotificationCountResponse;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name = "Управление уведомлениями")
@RestController
@RequestMapping(PathConstant.URL + "notifications")
public interface NotificationController {

    @GetMapping("/settings")
    @Operation(summary = "Получение настроек уведомлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Not Authorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    ResponseEntity<NotificationSettingsDto> getSettings();

    @PutMapping("/settings")
    @Operation(summary = "Обновление настроек уведомлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Not Authorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    ResponseEntity<DefaultResponse> updateSettings(SettingsRequest settings);

    @GetMapping
    @Operation(summary = "Получение всех уведомлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Not Authorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    ResponseEntity<SentNotificationsDto> getNotifications();

    @GetMapping("/count")
    @Operation(summary = "Получение количества отправленых уведомлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Not Authorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")})
    ResponseEntity<NotificationCountResponse> getCountNotifications();
}
