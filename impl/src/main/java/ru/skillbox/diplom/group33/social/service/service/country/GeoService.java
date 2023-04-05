package ru.skillbox.diplom.group33.social.service.service.country;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.controller.country.GeoRefreshControlLer;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.dto.country.GeoJsonDto;
import ru.skillbox.diplom.group33.social.service.mapper.country.GeoMapper;
import ru.skillbox.diplom.group33.social.service.model.country.City;
import ru.skillbox.diplom.group33.social.service.model.country.Country;
import ru.skillbox.diplom.group33.social.service.repository.country.CityRepository;
import ru.skillbox.diplom.group33.social.service.repository.country.CountryRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoService {
    private final CountryRepository repositoryCountry;
    private final CityRepository repositoryCity;

    private final GeoRefreshControlLer geoRefreshControlLer;

    private final GeoMapper mapper;


    public List<CountryDto> getAll() {
        log.info("IN GeoService getAll");
        return mapper.converToCountryDtoList(repositoryCountry.findAll());
    }


    public List<CityDto> getById(Long id) {
        log.info("IN GeoService getById - id: {}", id);
        return mapper.converToCityDtoList(repositoryCity.findAllByCountryId(id));
    }


    @Scheduled(cron = "@hourly")
    public void updateCountry() {
        List<GeoJsonDto> areas = Collections.emptyList();
        areas = geoRefreshControlLer.getAreas();

        if (areas.isEmpty()) {
            return;
        }
        areas.stream().forEach(area -> {
            if (!area.getName().equals("Другие регионы") && !repositoryCountry.existsByTitle(area.getName()) && area.getParentId() == null) {
                Country country = new Country();
                country.setTitle(area.getName());
                country.setIsDeleted(false);
                repositoryCountry.save(country);
            }
        });
    }

    @Scheduled(cron = "@hourly")
    public void updateCity() {
        List<GeoJsonDto> areas = Collections.emptyList();
        areas = geoRefreshControlLer.getAreas();

        areas.stream()
                .filter(areaCountry -> !areaCountry.getName().equals("Другие регионы") && repositoryCountry.existsByTitle(areaCountry.getName()) && areaCountry.getParentId() == null)
                .forEach(areaCountry -> {
                    Long countryId = repositoryCountry.getCountryByTitle(areaCountry.getName()).getId();

                    Arrays.stream(areaCountry.getAreas())
                            .forEach(areaCity -> {
                                if (areaCity.getAreas().length == 0 && !repositoryCity.existsByTitle(areaCity.getName())) {
                                    City city = new City();
                                    city.setTitle(areaCity.getName());
                                    city.setCountryId(countryId);
                                    city.setIsDeleted(false);
                                    repositoryCity.save(city);
                                } else {
                                    Arrays.stream(areaCity.getAreas())
                                            .filter(innerAreaCity -> !repositoryCity.existsByTitle(innerAreaCity.getName()))
                                            .forEach(innerAreaCity -> {
                                                City city = new City();
                                                city.setTitle(innerAreaCity.getName());
                                                city.setCountryId(countryId);
                                                city.setIsDeleted(false);
                                                repositoryCity.save(city);
                                            });
                                }
                            });
                });
    }



}
