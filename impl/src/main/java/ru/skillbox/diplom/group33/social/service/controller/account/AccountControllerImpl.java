package ru.skillbox.diplom.group33.social.service.controller.account;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountOnlineDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;

@Slf4j
@RestController
@AllArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDto> create(AccountDto dto) {
        log.info("IN AccountControllerImpl - create, dto :" + dto);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<AccountDto> getMe() {
        log.info("IN AccountControllerImpl - getById");
        return ResponseEntity.ok(accountService.getAccount());
    }

    @Override
    public ResponseEntity<Page<AccountDto>> search(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("IN AccountControllerImpl - search, accountSearchDto:" + accountSearchDto);
        return ResponseEntity.ok(accountService.search(accountSearchDto,page));
    }

    @Override
    public ResponseEntity<AccountDto> getById(Long id) {
        log.info("IN AccountControllerImpl - getById, id : " + id);
        return ResponseEntity.ok(accountService.getById(id));
    }

    @Override
    public ResponseEntity<AccountDto> update(AccountDto accountDto) {
        log.info("IN AccountControllerImpl - update, accountDto:" + accountDto);
        return ResponseEntity.ok(accountService.update(accountDto));
    }

    @Override
    public ResponseEntity<String> deleteMe() {
        log.info("IN AccountControllerImpl - delete");
        accountService.deleteAccount();
        return ResponseEntity.ok("OK");
    }

    @Override
    public ResponseEntity<Page<AccountDto>> getAll(AccountSearchDto accountSearchDto, Pageable page) {
        log.info("IN AccountControllerImpl - search, accountSearchDto:" + accountSearchDto);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity deleteById(Long id) {
        log.info("IN AccountControllerImpl - deleteById, id: " + id);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}