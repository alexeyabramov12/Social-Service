package ru.skillbox.diplom.group33.social.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skillbox.diplom.group33.social.service.dto.base.BaseSearchDto;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSearchDto extends BaseSearchDto {
    private String email;
    private String phone;
    private String photo;
    private String about;
    private String city;
    private String country;
    private String token;
    private StatusCode statusCode;
    private String firstName;
    private String lastName;
    private ZonedDateTime regDate;
    private ZonedDateTime birthDate;
    private String messagePermission;
    private ZonedDateTime lastOnlineTime;
    private Boolean isOnline;
    private Boolean isBlocked;
    private String photoId;
    private String photoName;
    private RoleEnum role;
    private Instant createdOn;
    private Instant updatedOn;
    private String password;
}
