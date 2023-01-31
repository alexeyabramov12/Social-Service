package ru.skillbox.diplom.group33.social.service.auth.dto;


import lombok.Data;
@Data
public class AuthenticateDto {
    private String email;
    private String password;
}
