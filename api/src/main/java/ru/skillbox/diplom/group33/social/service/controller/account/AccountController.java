package ru.skillbox.diplom.group33.social.service.controller.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.diplom.group33.social.service.controller.base.BaseController;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountSearchDto;
import ru.skillbox.diplom.group33.social.service.utils.path.PathConstant;

@RequestMapping(PathConstant.URL + "account/")
public interface AccountController extends BaseController<AccountDto, AccountSearchDto> {

    @GetMapping(value = "me")
    ResponseEntity<AccountDto> getMe();

    @GetMapping(value = "search")
    ResponseEntity<Page<AccountDto>> search(AccountSearchDto accountSearchDto, Pageable page);

    @GetMapping(value = "{id}")
    ResponseEntity<AccountDto> getById(@PathVariable Long id);

    @PutMapping(value = "me")
    ResponseEntity<AccountDto> update(@RequestBody AccountDto accountDto);

    @DeleteMapping(value = "me")
    ResponseEntity<String> deleteMe();
}