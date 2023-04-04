package ru.skillbox.diplom.group33.social.service.model.account;

import lombok.*;
import ru.skillbox.diplom.group33.social.service.model.auth.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account extends User {

    @Column(name = "reg_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime regDate;
    @Column(name = "birth_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime birthDate;
    @Column(name = "phone")
    private String phone;
    @Column(name = "photo")
    private String photo;
    @Column(name = "about")
    private String about;
    @Column(name = "message_permission")
    private String messagePermission;
    @Column(name = "last_online_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime lastOnlineTime;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "city_name")
    private String city;
    @Column(name = "country_name")
    private String country;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "confirmation_code")
    private String confirmationCode;
    @Column(name = "time_confirmation_code", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime timeConfirmationCode;
    @Column(name = "is_email_confirm")
    private Boolean isEmailConfirm;
    @Column(name = "is_online")
    private Boolean isOnline;
    @Column(name = "create_on", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime createOn;
    @Column(name = "update_on", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime updateOn;
    @Column(name = "time", columnDefinition = "timestamp with time zone")
    private ZonedDateTime time;
}