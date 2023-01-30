package ru.skillbox.diplom.group33.social.service.dto.auth;


import lombok.Data;
@Data
public class AuthenticateDto {
    private String email;
    private String password;
}
