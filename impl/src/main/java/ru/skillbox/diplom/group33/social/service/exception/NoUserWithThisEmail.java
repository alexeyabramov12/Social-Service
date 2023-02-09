package ru.skillbox.diplom.group33.social.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoUserWithThisEmail extends RuntimeException {

}
