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

    private ZonedDateTime regDate;
    private ZonedDateTime birthDate;
    private String phone;
    private String photo;
    private String author;
    private String about;
    private String messagePermission;
    private ZonedDateTime lastOnlineTime;
    private Boolean isBlocked;
    private String userType;
    private String city;
    private String country;
    private Boolean isDeleted;
    private String confirmationCode;
    private ZonedDateTime timeConfirmationCode;
    private Boolean isEmailConfirm;
    private Boolean isOnline;
    private ZonedDateTime createOn;
    private ZonedDateTime updateOn;
    private ZonedDateTime birthDateFrom;
    private ZonedDateTime birthDateTo;
    private Integer ageTo;
    private Integer ageFrom;
    private StatusCode statusCode;
    private RoleEnum role;
}