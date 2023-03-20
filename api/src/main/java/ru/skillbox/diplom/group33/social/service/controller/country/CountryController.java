package ru.skillbox.diplom.group33.social.service.controller.country;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

import java.util.List;

@RequestMapping(PathConstant.URL + "geo")
public interface CountryController {

    @GetMapping("/countries")
    @Operation(summary = "Получение списка стран")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<List<CountryDto>> getAll();

    @GetMapping("/cities/{id}")
    @Operation(summary = "Получение списка городов по id страны")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "500", content = @Content(mediaType = ""))})
    ResponseEntity<List<CityDto>> getById(@PathVariable("id") Long id);


}
