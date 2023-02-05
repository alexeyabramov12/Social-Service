package ru.skillbox.diplom.group33.social.service.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticateResponseDto {
    private String accessToken;
    private String refreshToken;
}
