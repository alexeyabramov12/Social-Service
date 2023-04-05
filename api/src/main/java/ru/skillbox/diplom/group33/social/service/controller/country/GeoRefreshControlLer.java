package ru.skillbox.diplom.group33.social.service.controller.country;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skillbox.diplom.group33.social.service.dto.country.GeoJsonDto;

import java.util.List;

@Tag(name = "Feign geo service", description = "Импорт стран и городов с помощью API")
@FeignClient(name = "GeoApi", url = "https://api.hh.ru")
public interface GeoRefreshControlLer {
    @Operation(summary = "Импорт стран и городов с помощью API")
    @GetMapping(value = "/areas", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Страны и города загружены",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeoJsonDto.class)))
    List<GeoJsonDto> getAreas();

}
