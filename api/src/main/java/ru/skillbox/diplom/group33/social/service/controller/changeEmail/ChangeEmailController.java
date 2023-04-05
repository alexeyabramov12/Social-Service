package ru.skillbox.diplom.group33.social.service.controller.changeEmail;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@Tag(name = "Смена электроной почты")
@RequestMapping(PathConstant.URL)
public interface ChangeEmailController {
    @PostMapping("shift-email")
    @Operation(summary = "Окно для установки новой электронной почты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "новоя электронная почта установлена", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    void enteringNewEmailAndCheckCaptcha(@RequestBody ChangeEmailDto changeEmailDto);
}
