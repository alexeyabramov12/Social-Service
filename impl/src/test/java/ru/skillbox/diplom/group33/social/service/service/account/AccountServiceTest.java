package ru.skillbox.diplom.group33.social.service.service.account;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.config.storage.CloudinaryEndPoints;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestExecutionListeners(WithSecurityContextTestExecutionListener.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AccountMapper accountMapper;


    @Mock
    private Authentication authentication;

    @Mock
    private Account account;

    @Mock
    private AccountDto accountDto;

    @Mock
    private CloudinaryEndPoints cloudinaryEndPoints;


    @InjectMocks
    private AccountService accountService;


    @BeforeEach
    void setUp() {
        JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
                , "test", null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Test
    public void testCreateAccount() {
        // Given
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        when(accountMapper.userToAccount(user)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        accountService.createAccount(user);

        verify(accountMapper).userToAccount(user);
        verify(accountRepository).save(account);
        verify(notificationService).createSettings(1L);
    }

    @Test
    public void testGetAccountsByIds() {
        // Setup
        Long id1 = 1L;
        Long id2 = 2L;
        Account account1 = new Account();
        account1.setId(id1);
        Account account2 = new Account();
        account2.setId(id2);
        List<Long> accountIds = Arrays.asList(id1, id2);
        when(accountRepository.findAllById(accountIds)).thenReturn(Arrays.asList(account1, account2));

        List<Account> accounts = accountService.getAccountsByIds(accountIds);

        assertEquals(2, accounts.size());
        assertEquals(id1, accounts.get(0).getId());
        assertEquals(id2, accounts.get(1).getId());
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        AccountDto accountDto = new AccountDto();
        accountDto.setId(id);
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountMapper.convertToDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(accountRepository).findById(id);
        verify(accountMapper).convertToDto(account);
    }

    @Test
    public void testGetByIdSuccess() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        AccountDto accountDto = new AccountDto();
        accountDto.setId(id);

        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        AccountDto result = accountService.getById(id);

        assertNull(result);
    }


}

