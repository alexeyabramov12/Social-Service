package ru.skillbox.diplom.group33.social.service.dto.captcha;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaDto {

    private String secret;

    private String image;

}
