package ru.skillbox.diplom.group33.social.service.config.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NoUserWithThisEmail extends UsernameNotFoundException {
    public NoUserWithThisEmail(String msg) {
        super(msg);
    }
}
