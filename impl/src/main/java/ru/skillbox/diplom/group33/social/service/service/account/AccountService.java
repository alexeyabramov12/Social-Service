package ru.skillbox.diplom.group33.social.service.service.account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.config.storage.CloudinaryEndPoints;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group33.social.service.dto.storage.StorageDto;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.account.Account_;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;
import ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.skillbox.diplom.group33.social.service.utils.account.SecurityUtils.getJwtUsers;
import static ru.skillbox.diplom.group33.social.service.utils.specification.SpecificationUtils.*;


@Slf4j
@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final NotificationService notificationService;
    private final AccountMapper mapper;
    private final CloudinaryEndPoints cloudinaryEndPoints;



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

    public Page<AccountDto> search(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("IN AccountService - search, accountSearchDto: " + accountSearchDto);
        if (accountSearchDto.getAuthor() == null) {
            return repository.findAll(getSpecificationByAllParameters(accountSearchDto), page).map(mapper::convertToDto);
        } else {
            return repository.findAll(getSpecificationByAuthor(accountSearchDto), page).map(mapper::convertToDto);
        }
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
        AccountDto account = getAccount();
        account.setPhoto(cloudinaryEndPoints.getDefaultPhoto());
        repository.save(mapper.convertToAccount(account));
    }

    public List<Long> getAccountsIds() {
        log.info("AccountService - getAccountsIds");
        Page<Account> pageData = repository.findAll(PageRequest.of(0, 20));
        List<Long> listIds = pageData.getContent().stream()
                .map(Account::getId).collect(Collectors.toList());
        while (pageData.hasNext()) {
            pageData = repository.findAll(pageData.nextPageable());
            listIds.addAll(pageData.getContent().stream()
                    .map(Account::getId).collect(Collectors.toList()));
        }
        return listIds;
    }


    private static Specification<Account> getSpecificationByAuthor(AccountSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(notIn(Account_.id, searchDto.getBlockedByIds(), true))
                .and(likeLowerCase(Account_.firstName, searchDto.getAuthor(), true))
                .and(notIn(Account_.email, Collections.singletonList(getJwtUsers().getEmail()), true))
                .or(likeLowerCase(Account_.lastName, searchDto.getAuthor(), true))
                .and(notIn(Account_.email, Collections.singletonList(getJwtUsers().getEmail()), true));
    }

    private static Specification<Account> getSpecificationByAllParameters(AccountSearchDto searchDto) {
        return getBaseSpecification(searchDto)
                .and(notIn(Account_.id, searchDto.getBlockedByIds(), true))
                .and(in(Account_.id, searchDto.getIds(), true))
                .and(notIn(Account_.email, Collections.singletonList(getJwtUsers().getEmail()), true))
                .and(likeLowerCase(Account_.firstName, searchDto.getFirstName(), true))
                .and(likeLowerCase(Account_.lastName, searchDto.getLastName(), true))
                .and(likeLowerCase(Account_.country, searchDto.getCountry(), true))
                .and(likeLowerCase(Account_.city, searchDto.getCity(), true))
                .and(between(Account_.birthDate,
                        searchDto.getAgeTo() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeTo()),
                        searchDto.getAgeFrom() == null ? null : ZonedDateTime.now().minusYears(searchDto.getAgeFrom()), true));
    }


    public AccountDto updateAccountPhoto(StorageDto storageDto) {
        AccountDto account = getAccount();
        account.setPhoto(storageDto.getPhotoPath());
        repository.save(mapper.convertToAccount(account));
        return account;
    }
}