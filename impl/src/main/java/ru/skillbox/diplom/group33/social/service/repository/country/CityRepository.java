package ru.skillbox.diplom.group33.social.service.repository.country;

import org.springframework.stereotype.Repository;
import ru.skillbox.diplom.group33.social.service.model.country.City;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;


@Repository
public interface CityRepository extends BaseRepository<City> {


    List<City> findAllByCountryId(Long id);


}
