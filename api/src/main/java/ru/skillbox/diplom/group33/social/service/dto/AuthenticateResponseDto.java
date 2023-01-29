package ru.skillbox.diplom.group33.social.service.dto;

import lombok.*;
@Data
@AllArgsConstructor
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;
}
