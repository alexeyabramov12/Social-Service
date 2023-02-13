package ru.skillbox.diplom.group33.social.service.dto.country;

import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseDto;

import java.util.List;


@Getter
@Setter
public class CountryDto extends BaseDto {

    private String title;

    private List<String> cities;

}
