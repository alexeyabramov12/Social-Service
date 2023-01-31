package ru.skillbox.diplom.group33.social.service.captcha.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaDto {

    private String secret;

    private String image;

}
