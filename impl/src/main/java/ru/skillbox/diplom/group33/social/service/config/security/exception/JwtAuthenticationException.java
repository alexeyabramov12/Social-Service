package ru.skillbox.diplom.group33.social.service.config.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String s) {
        super(s);
    }
}
