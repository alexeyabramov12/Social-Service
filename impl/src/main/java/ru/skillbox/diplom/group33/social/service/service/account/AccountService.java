package ru.skillbox.diplom.group33.social.service.service.account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;
import ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils;


@Slf4j
@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final NotificationService notificationService;
    private final AccountMapper mapper;

    public void createAccount(User user) {
        log.info("AccountService - createAccount, user : " + user);
        Account account = repository.save(mapper.userToAccount(user));
        notificationService.createSettings(account.getId());
    }

    public AccountDto getAccount() {
        log.info("IN AccountService getAccount");
        return mapper.convertToDto(repository.findById(SecurityUtils.getJwtUsersId())
                .orElseThrow(NullPointerException::new));
    }

    public AccountDto search(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("IN AccountService - search, accountSearchDto: " + accountSearchDto);
        return null;
    }

    public AccountDto getById(Long id) {
        log.info("IN AccountService - getById, id: " + id);
        return mapper.convertToDto(repository.findById(id).orElseThrow(NullPointerException::new));
    }

    public AccountDto update(AccountDto accountDto) {
        Long userId = SecurityUtils.getJwtUsersId();
        Account accountToSave = mapper.convertToAccount(accountDto, repository.findById(userId).orElse(new Account()));
        log.info("IN AccountService - updateAccount, accountDto: " + accountDto);
        return mapper.convertToDto(repository.save(accountToSave));
    }

    public void deleteAccount() {
        Long id = SecurityUtils.getJwtUsersId();
        log.info("In AccountService - deleteAccount, id:" + id);
        repository.deleteById(id);
    }
}