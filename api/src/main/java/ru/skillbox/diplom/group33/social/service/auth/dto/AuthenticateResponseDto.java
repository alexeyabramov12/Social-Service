package ru.skillbox.diplom.group33.social.service.auth.dto;

import lombok.*;
@Data
@AllArgsConstructor
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;
}
