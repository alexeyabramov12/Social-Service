package ru.skillbox.diplom.group33.social.service.controller.country;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.service.country.GeoService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GeoControllerImpl implements CountryController {

    private final GeoService geoService;


    @Override
    public ResponseEntity<List<CountryDto>> getAll() {
        log.info("IN GeoControllerImpl getAll");
        return ResponseEntity.status(HttpStatus.OK).body(geoService.getAll());
    }

    @Override
    public ResponseEntity<List<CityDto>> getById(Long id) {
        log.info("IN GeoControllerImpl getById - id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(geoService.getById(id));
    }



}
