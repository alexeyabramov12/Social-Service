package ru.skillbox.diplom.group33.social.service.dto;
import lombok.Data;
@Data
public class RegistrationDto {

    private String email;
    public String password1;
    private String password2;
    private String firstName;
    private String lastName;
    private String captchaCode;
    private String captchaSecret;
}
