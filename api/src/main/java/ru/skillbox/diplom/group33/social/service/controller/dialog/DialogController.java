package ru.skillbox.diplom.group33.social.service.controller.dialog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.DialogsRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.MessageRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.StatusMessageReadRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.UnreadCountRs;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name = "Система управления диалогами")
@RequestMapping(PathConstant.URL + "dialogs")
public interface DialogController {

    @PutMapping("/{companionId}")
    @Operation(summary = "Обновление статуса прочтения сообщения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное обновление", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<StatusMessageReadRs> updateReadStatus(@PathVariable Long companionId);

    @GetMapping()
    @Operation(summary = "Получение всех диалогов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<DialogsRs> getAllDialogs(@RequestParam(defaultValue = "0") Integer offset,
                                            @RequestParam(defaultValue = "20") Integer itemPerPage);


    @GetMapping("/messages")
    @Operation(summary = "Получение всех сообщений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<MessageRs> getAllMessages(@RequestParam Long companionId,
                                             @RequestParam(defaultValue = "0") Integer offset,
                                             @RequestParam(defaultValue = "20") Integer itemPerPage);

    @GetMapping("/unreaded")
    @Operation(summary = "Получение количества непрочитанных сообщений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<UnreadCountRs> getUnreadCount();


}
