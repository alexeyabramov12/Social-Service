package ru.skillbox.diplom.group33.social.service.dto.auth;
import lombok.Data;
@Data
public class RegistrationDto {

    private String email;
    private String password1;
    private String password2;
    private String firstName;
    private String lastName;
    private String code;
    private String token;
}
