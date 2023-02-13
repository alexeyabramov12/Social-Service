package ru.skillbox.diplom.group33.social.service.dto.country;

import lombok.Getter;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.util.List;

@Getter
@Setter
public class CountrySearchDto extends BaseSearchDto {

    private String title;

    private List<String> cities;
}
