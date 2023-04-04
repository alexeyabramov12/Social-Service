package ru.skillbox.diplom.group33.social.service.service.dialog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.socket.handler.NotificationHandler;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.DialogDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.DialogSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.ReadStatusDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.DialogsRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.MessageRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.StatusMessageReadRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.response.UnreadCountRs;
import ru.skillbox.diplom.group33.social.service.dto.dialog.setStatusMessageDto.SetStatusMessageReadDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.unreadCountDto.UnreadCountDto;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.mapper.dialog.DialogMapper;
import ru.skillbox.diplom.group33.social.service.mapper.dialog.message.MessageMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.dialog.Dialog;
import ru.skillbox.diplom.group33.social.service.model.dialog.Dialog_;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.Message_;
import ru.skillbox.diplom.group33.social.service.model.dialog.message.ReadStatus;
import ru.skillbox.diplom.group33.social.service.repository.dialog.DialogRepository;
import ru.skillbox.diplom.group33.social.service.repository.dialog.message.MessageRepository;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.dto.notification.type.NotificationType.MESSAGE;
import static ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils.getJwtUserIdFromSecurityContext;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.equal;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.getBaseSpecification;


@Slf4j
@Service
@RequiredArgsConstructor
public class DialogService {

    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;
    private final NotificationHandler notificationHandler;
    private final DialogMapper dialogMapper;
    private final AccountMapper accountMapper;
    private final MessageMapper messageMapper;
    private final AccountService accountService;


    public StatusMessageReadRs updateReadStatus(Long companionId) {
        StatusMessageReadRs readRs = new StatusMessageReadRs("", "", ZonedDateTime.now().toEpochSecond());

        Dialog dialog = getDialog(getJwtUserIdFromSecurityContext(), companionId);
        List<Message> messageList = messageRepository.findAllByDialogIdAndAuthorIdAndReadStatus(
                dialog.getId(), companionId, ReadStatus.SEND
        );
        messageList.forEach(m -> m.setReadStatus(ReadStatus.READ));
        messageRepository.saveAll(messageList);

        log.info("In DialogService updateReadStatus: update the read status of messages with the user - {}", companionId);
        readRs.setData(new SetStatusMessageReadDto("ok"));
        return readRs;
    }

    public DialogsRs getAllDialogs(Integer offset, Integer itemPerPage) {
        Long userId = getJwtUserIdFromSecurityContext();
        DialogSearchDto searchDto = new DialogSearchDto();
        searchDto.setUserId(userId);
        searchDto.setIsDeleted(false);

        List<Dialog> dialogList = dialogRepository
                .findAll(getSpecification(searchDto), PageRequest.of(offset, itemPerPage))
                .toList();

        List<Account> accountList = accountService.getAccountsByIds(getAccountIds(dialogList));
        Map<Long, AccountDto> accountDtoMap = new HashMap<>();
        accountList.forEach(account -> accountDtoMap.put(account.getId(), accountMapper.convertToDto(account)));

        log.info("In DialogService getAllDialogs: get all dialogs for a user - {}", userId);
        List<DialogDto> listDialogDto = getListDialogDto(dialogList, accountDtoMap);
        return dialogMapper.initDialogRs("", "", ZonedDateTime.now().toEpochSecond(), listDialogDto, offset, itemPerPage);
    }

    public MessageRs getAllMessages(Long companionId, Integer offset, Integer itemPerPage) {
        MessageRs response = new MessageRs("", "", ZonedDateTime.now().toEpochSecond());
        response.setOffSet(offset);
        response.setPerPage(itemPerPage);
        response.setTotal(messageRepository.count());

        MessageSearchDto searchDto = getMessageSearchDto(getJwtUserIdFromSecurityContext(), companionId);
        List<Message> messageList = new ArrayList<>(messageRepository.findAll(getSpecification(searchDto),
                Sort.by(Sort.Direction.DESC, "time")));

        response.setData(messageList.stream().map(messageMapper::convertEntityToShortDto).collect(Collectors.toList()));
        log.info("In DialogService getAllMessages: get all messages with companionId - {}", companionId);
        return response;
    }

    public UnreadCountRs getUnreadCount() {
        Long userId = getJwtUserIdFromSecurityContext();
        UnreadCountRs unreadCountRs = new UnreadCountRs("", "", ZonedDateTime.now().toEpochSecond());

        MessageSearchDto searchDto = new MessageSearchDto();
        searchDto.setRecipientId(userId);
        searchDto.setReadStatus(ReadStatusDto.SEND);
        Long unreadCount = (long) messageRepository.findAll(getSpecification(searchDto)).size();

        unreadCountRs.setData(new UnreadCountDto(unreadCount));
        log.info("In DialogService getUnreadCount: getting the number of unread messages for a user - {}", userId);
        return unreadCountRs;
    }

    @Async
    @KafkaListener(topics = "${topic.names.message}")
    public void listenMessage(MessageDto messageDto) {
        log.info("In DialogService listenMessage: messageDto - {}", messageDto);
        createMessage(messageDto);
    }

    private void createMessage(MessageDto messageDto) {
        Dialog dialog = getDialog(messageDto.getAuthorId(), messageDto.getRecipientId());
        messageDto.setDialogId(dialog.getId());
        messageDto.setReadStates(ReadStatusDto.SEND);
        messageDto.setIsDeleted(false);

        Message message = messageRepository.save(messageMapper.convertToEntity(messageDto));
        notificationHandler.sendNotification(message.getRecipientId(), message.getAuthorId(),
                MESSAGE, "\"" + message.getMessageText() + "\"");
        log.info("In DialogService createMessage: create message - {}", message);

        dialog.setLastMessage(message);
        dialogRepository.save(dialog);
    }


    private Dialog getDialog(Long authorId, Long companionId) {
        DialogSearchDto searchDto = new DialogSearchDto();
        searchDto.setIsDeleted(false);
        searchDto.setUserId(authorId);
        searchDto.setConversationPartnerId(companionId);

        List<Dialog> dialogs = dialogRepository.findAll(getSpecification(searchDto));

        if (dialogs.size() == 0) {
            Dialog dialog = new Dialog();
            dialog.setIsDeleted(false);
            dialog.setUserId1(authorId);
            dialog.setUserId2(companionId);
            log.info("In DialogService getDialog: create dialog - {}", dialog);

            return dialogRepository.save(dialog);
        }
        return dialogs.get(0);
    }

    private MessageSearchDto getMessageSearchDto(Long authorId, Long recipientId) {
        MessageSearchDto dto = new MessageSearchDto();
        Dialog dialog = getDialog(authorId, recipientId);
        dto.setIsDeleted(false);
        dto.setDialogId(dialog.getId());
        dto.setReadStatus(null);
        return dto;
    }


    private List<Long> getAccountIds(List<Dialog> dialogList) {
        return dialogList.stream().map(dialog -> dialog.getUserId1().equals(getJwtUserIdFromSecurityContext())
                ? dialog.getUserId2()
                : dialog.getUserId1()).collect(Collectors.toList());
    }

    private List<DialogDto> getListDialogDto(List<Dialog> dialogList, Map<Long, AccountDto> accountDtoMap) {
        return dialogList.stream().map(dialog -> {
            Long conversationPartner = dialog.getUserId1().equals(getJwtUserIdFromSecurityContext())
                    ? dialog.getUserId2()
                    : dialog.getUserId1();

            Long unreadCount = (long) messageRepository.findAllByDialogIdAndRecipientIdAndReadStatus(
                    dialog.getId(), getJwtUserIdFromSecurityContext(), ReadStatus.SEND).size();

            DialogDto dto = dialogMapper.convertToDto(dialog);
            dto.setConversationPartner(accountDtoMap.get(conversationPartner));
            dto.setUnreadCount(unreadCount);
            return dto;

        }).collect(Collectors.toList());
    }

    private Specification<Dialog> getSpecification(DialogSearchDto searchDto) {
        if (searchDto.getUserId() != null & searchDto.getConversationPartnerId() != null) {
            return getBaseSpecification(searchDto)
                    .and((equal(Dialog_.userId1, searchDto.getUserId(), true)
                            .and(equal(Dialog_.userId2, searchDto.getConversationPartnerId(), true)))
                            .or(equal(Dialog_.userId1, searchDto.getConversationPartnerId(), true)
                                    .and(equal(Dialog_.userId2, searchDto.getUserId(), true))));
        } else {
            return getBaseSpecification(searchDto)
                    .and(equal(Dialog_.userId1, searchDto.getUserId(), true))
                    .or(equal(Dialog_.userId2, searchDto.getUserId(), true));
        }
    }

    private Specification<Message> getSpecification(MessageSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(equal(Message_.dialogId, searchDto.getDialogId(), true))
                .and((equal(Message_.recipientId, searchDto.getRecipientId(), true)
                        .and(equal(Message_.readStatus, messageMapper.convertRedStatusDtoToReadStatus(searchDto.getReadStatus()), true))));
    }

}
