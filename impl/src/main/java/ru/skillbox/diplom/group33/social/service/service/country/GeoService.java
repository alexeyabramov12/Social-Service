package ru.skillbox.diplom.group33.social.service.service.country;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.mapper.country.GeoMapper;
import ru.skillbox.diplom.group33.social.service.repository.country.CityRepository;
import ru.skillbox.diplom.group33.social.service.repository.country.CountryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoService {
    private final CountryRepository repositoryCountry;
    private final CityRepository repositoryCity;

    private final GeoMapper mapper;


    public List<CountryDto> getAll() {
        log.info("IN GeoService getAll");
        return mapper.converToCountryDtoList(repositoryCountry.findAll());
    }


    public List<CityDto> getById(Long id) {
        log.info("IN GeoService getById - id: {}", id);
        return mapper.converToCityDtoList(repositoryCity.findAllByCountryId(id));
    }


}
