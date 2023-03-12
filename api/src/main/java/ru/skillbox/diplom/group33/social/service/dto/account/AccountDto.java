package ru.skillbox.diplom.group33.social.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.auth.UserDto;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends UserDto {

    private String phone;
    private String author;
    private ZonedDateTime birthDateFrom;
    private ZonedDateTime birthDateTo;
    private ZonedDateTime birthDate;
    private String city;
    private String country;
    private Boolean isBlocked;
    private Integer ageTo;
    private Integer ageFrom;
    private StatusCode statusCode;
    private RoleEnum role;
}