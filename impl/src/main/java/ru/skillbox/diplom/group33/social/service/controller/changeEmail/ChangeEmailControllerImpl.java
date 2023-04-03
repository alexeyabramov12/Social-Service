package ru.skillbox.diplom.group33.social.service.controller.changeEmail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.changeEmail.ChangeEmailDto;
import ru.skillbox.diplom.group33.social.service.service.changeEmail.ChangeEmailService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChangeEmailControllerImpl implements ChangeEmailController {
    private final ChangeEmailService changeEmailService;
    @Override
    public void enteringNewEmailAndCheckCaptcha(ChangeEmailDto changeEmailDto) {
       changeEmailService.enteringNewEmailAndCheckCaptcha(changeEmailDto);
    }
}
