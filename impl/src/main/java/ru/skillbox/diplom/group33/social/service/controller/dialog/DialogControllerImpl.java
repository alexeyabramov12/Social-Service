package ru.skillbox.diplom.group33.social.service.controller.dialog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.DialogsRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.MessageRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.StatusMessageReadRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.UnreadCountRs;
import ru.skillbox.diplom.group33.social.service.service.dialog.DialogService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DialogControllerImpl implements DialogController {

    private final DialogService service;


    @Override
    public ResponseEntity<StatusMessageReadRs> updateReadStatus(Long companionId) {
        log.info("In DialogController updateReadStatus: companionId - {}", companionId);
        return ResponseEntity.ok(service.updateReadStatus(companionId));
    }

    @Override
    public ResponseEntity<DialogsRs> getAllDialogs(Integer offset, Integer itemPerPage) {
        log.info("In DialogController getDialogs:");
        return ResponseEntity.ok(service.getAllDialogs(offset, itemPerPage));
    }

    @Override
    public ResponseEntity<MessageRs> getAllMessages(Long companionId, Integer offset, Integer itemPerPage) {
        log.info("In DialogController getMessages: companionId - {}", companionId);
        return ResponseEntity.ok(service.getAllMessages(companionId, offset, itemPerPage));
    }

    @Override
    public ResponseEntity<UnreadCountRs> getUnreadCount() {
        log.info("In DialogController getUnreadCount");
        return ResponseEntity.ok(service.getUnreadCount());
    }
}
