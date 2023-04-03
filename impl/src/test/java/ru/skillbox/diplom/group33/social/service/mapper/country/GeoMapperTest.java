package ru.skillbox.diplom.group33.social.service.mapper.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.model.country.City;
import ru.skillbox.diplom.group33.social.service.model.country.Country;
import ru.skillbox.diplom.group33.social.service.service.country.GeoService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoMapperTest {


    private GeoMapper geoMapper;

    @InjectMocks
    private GeoService geoService;

    List<City> city = new ArrayList<>();


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        geoMapper = Mappers.getMapper(GeoMapper.class);

    }

    @Test
    public void converToCountryDto() {

        Country country = new Country();
        country.setId(1L);
        country.setTitle("title");
        country.setIsDeleted(false);

        CountryDto countryDto = geoMapper.converToCountryDto(country);

        assertEquals(countryDto.getId(), 1L);
        assertEquals(countryDto.getTitle(), "title");
    }

    @Test
    public void converToCountry() {

        CountryDto countryDto1 = new CountryDto();
        countryDto1.setId(1L);
        countryDto1.setTitle("title");
        countryDto1.setIsDeleted(false);

        Country country = geoMapper.converToCountry(countryDto1);

        assertEquals(country.getId(), 1L);
        assertEquals(country.getTitle(), "title");
    }

    @Test
    public void converToCountryDtoList() {


        Country country1 = new Country();
        country1.setId(1L);
        country1.setTitle("test1");
        country1.setIsDeleted(false);
        country1.setCity(city);

        Country country2 = new Country();
        country2.setId(2L);
        country2.setTitle("test2");
        country2.setIsDeleted(false);

        List<Country> countryList = List.of(country1, country2);

        List<CountryDto> countryDtoList = geoMapper.converToCountryDtoList(countryList);

        assertEquals(countryDtoList.size(), 2);
        assertEquals(countryDtoList.get(0).getTitle(), "test1");
        assertEquals(countryDtoList.get(0).getIsDeleted(), false);
        assertEquals(countryDtoList.get(1).getTitle(), "test2");
        assertEquals(countryDtoList.get(1).getIsDeleted(), false);
    }

    @Test
    public void converToCityDto() {

        City city = new City();
        city.setId(1L);
        city.setTitle("New York");
        city.setIsDeleted(false);

        CityDto cityDto = geoMapper.converToCityDto(city);

        assertEquals(cityDto.getTitle(), "New York");
        assertEquals(cityDto.getId(), 1L);
    }

    @Test
    public void converToCity() {

        CityDto cityDto1 = new CityDto();
        cityDto1.setId(1L);
        cityDto1.setTitle("New York");
        cityDto1.setIsDeleted(false);

        City city1 = geoMapper.converToCity(cityDto1);

        assertEquals(city1.getTitle(), "New York");
        assertEquals(city1.getId(), 1L);
    }

    @Test
    public void converToCityDtoList() {

        City city1 = new City();
        city1.setId(1L);
        city1.setTitle("New York");

        City city2 = new City();
        city2.setId(2L);
        city2.setTitle("Shanghai");

        List<City> cityList = List.of(city1, city2);

        List<CityDto> cityDtoList = geoMapper.converToCityDtoList(cityList);

        assertEquals(cityDtoList.size(), 2);
        assertEquals(cityDtoList.get(0).getTitle(), "New York");
        assertEquals(cityDtoList.get(0).getId(), 1L);
        assertEquals(cityDtoList.get(1).getTitle(), "Shanghai");
        assertEquals(cityDtoList.get(1).getId(), 2L);
    }
}
