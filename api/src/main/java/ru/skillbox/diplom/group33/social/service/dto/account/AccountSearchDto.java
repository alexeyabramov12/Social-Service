package ru.skillbox.diplom.group33.social.service.dto.account;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDto extends BaseSearchDto {
    private List<Long> ids;
    private List<Long> blockedByIds;
    private String author;
    private String firstName;
    private String lastName;
    private ZonedDateTime birthDateFrom;
    private ZonedDateTime birthDateTo;
    private String city;
    private String country;
    private Boolean isBlocked;
    private Long ageTo;
    private Long ageFrom;
}
