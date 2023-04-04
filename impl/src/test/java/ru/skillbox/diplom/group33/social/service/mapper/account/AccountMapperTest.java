package ru.skillbox.diplom.group33.social.service.mapper.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.auth.User;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountMapperTest {

    private AccountMapper accountMapper;

    @BeforeEach
    public void setUp() {
        accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Test
    public void convertToDto() {
        Account account = new Account();
        account.setId(1L);
        account.setPhone("911");

        AccountDto accountDto = accountMapper.convertToDto(account);

        assertNotNull(accountDto);
        assertEquals(account.getId(), accountDto.getId());
        assertEquals(account.getPhone(), accountDto.getPhone());
    }

    @Test
    public void convertToAccount() {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(1L);
        accountDto.setPhone("911");

        Account account = accountMapper.convertToAccount(accountDto);

        assertNotNull(account);
        assertEquals(accountDto.getId(), account.getId());
        assertEquals(accountDto.getPhone(), account.getPhone());
    }

    @Test
    public void userToAccount() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setEmail("john@example.com");

        Account account = accountMapper.userToAccount(user);

        assertNotNull(account);
        assertFalse(account.getIsBlocked());
        assertNotNull(account.getRegDate());
        assertNotNull(account.getCreateOn());
        assertNotNull(account.getUpdateOn());
    }

    @Test
    public void convertToAccountTwo() {
        Account existingAccount = new Account();
        existingAccount.setId(1L);
        existingAccount.setPhone("911");

        AccountDto accountDto = new AccountDto();
        accountDto.setPhone("911");

        Account account = accountMapper.convertToAccount(accountDto, existingAccount);

        assertNotNull(account);
        assertEquals(existingAccount.getId(), account.getId());
        assertEquals(accountDto.getPhone(), account.getPhone());
    }
}

