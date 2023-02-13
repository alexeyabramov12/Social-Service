package ru.skillbox.diplom.group33.social.service.controller.storage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

import java.io.IOException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Система хранения фото")
@RequestMapping(PathConstant.URL)
public interface StorageController {


    @PostMapping(value = "storage", consumes = {MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Загрузка фото")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная загрузка", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<StorageDto> upload(@RequestParam(value = "file", required = false)
                                      MultipartFile request) throws IOException;


}
