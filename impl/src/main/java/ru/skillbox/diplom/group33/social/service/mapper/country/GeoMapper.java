package ru.skillbox.diplom.group33.social.service.mapper.country;


import org.mapstruct.Mapper;
import ru.skillbox.diplom.group33.social.service.dto.country.CityDto;
import ru.skillbox.diplom.group33.social.service.dto.country.CountryDto;
import ru.skillbox.diplom.group33.social.service.model.country.City;
import ru.skillbox.diplom.group33.social.service.model.country.Country;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeoMapper {

    CountryDto converToCountryDto(Country country);

    Country converToCountry(CountryDto countryDto);

    List<CountryDto> converToCountryDtoList(List<Country> countriesList);

    List<Country> converToCountryList(List<CountryDto> countriesListDto);

    CityDto converToCityDto(City city);

    City converToCity(CityDto cityDto);

    List<CityDto> converToCityDtoList(List<City> cityList);

    List<City> converToCityList(List<CityDto> cityListDto);

}
