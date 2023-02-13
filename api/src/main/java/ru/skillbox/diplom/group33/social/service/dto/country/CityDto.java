package ru.skillbox.diplom.group33.social.service.dto.country;


import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;


@Getter
@Setter
public class CityDto extends BaseDto {

    private String title;

    private long countryId;
}
