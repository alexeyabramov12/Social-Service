package ru.skillbox.diplom.group33.social.service.repository.dialog.message;

import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.ReadStatus;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepository;

import java.util.List;

public interface MessageRepository extends BaseRepository<Message> {

    List<Message> findAllByDialogIdAndRecipientIdAndReadStatus(Long dialogId, Long recipientId, ReadStatus readStatus);

    List<Message> findAllByDialogIdAndAuthorIdAndReadStatus(Long dialogId, Long authorId, ReadStatus readStatus);

}
