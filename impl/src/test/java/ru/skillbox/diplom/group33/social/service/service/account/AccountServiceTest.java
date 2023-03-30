package ru.skillbox.diplom.group33.social.service.service.account;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import ru.skillbox.diplom.group33.social.service.config.security.JwtUser;
import ru.skillbox.diplom.group33.social.service.mapper.account.AccountMapper;
import ru.skillbox.diplom.group33.social.service.model.account.Account;
import ru.skillbox.diplom.group33.social.service.model.auth.User;
import ru.skillbox.diplom.group33.social.service.repository.account.AccountRepository;
import ru.skillbox.diplom.group33.social.service.service.notification.NotificationService;
import ru.skillbox.diplom.group33.social.service.utils.security.SecurityUtils;

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
    private SecurityContext securityContext;

    private AccountService accountService;


    @Mock
    JwtUser jwtUser = new JwtUser(1L, "Джон", "Сильвер", "test@test.ru"
            , "test", null);

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwtUser);
        Mockito.lenient().when(SecurityUtils.getJwtUserIdFromSecurityContext()).thenReturn(1L);

    }

    @Test
    public void testCreateAccount() {
        AccountService accountService = new AccountService(accountRepository,notificationService, accountMapper);
        // Given
        User user = new User();
        Account account = new Account();
        account.setId(1L);
        when(accountMapper.userToAccount(user)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        // When
        accountService.createAccount(user);

        // Then
        verify(accountMapper).userToAccount(user);
        verify(accountRepository).save(account);
        verify(notificationService).createSettings(1L);
    }

    /*@Test
    public void testGetAccount() {

        // Given
        Long userId = 1L;
        Account account = new Account();
        account.setId(userId);
        when(accountRepository.findById(userId)).thenReturn(Optional.of(account));
        AccountDto accountDto = new AccountDto();
        when(accountMapper.convertToDto(account)).thenReturn(accountDto);

        // When
        AccountDto result = accountService.getAccount();

        // Then
        assertEquals(accountDto, result);
        verify(accountRepository).findById(userId);
        verify(accountMapper).convertToDto(account);
    }*/

    /*@Test
    public void testSearch() {
        AccountService accountService = new AccountService(accountRepository,notificationService, accountMapper);
        // Given
        AccountSearchDto accountSearchDto = new AccountSearchDto();
        Pageable page = PageRequest.of(0, 10);
        Account account = new Account();
        Page<Account> accountPage = new PageImpl<>(Collections.singletonList(account), page, 1);
        AccountDto accountDto = new AccountDto();
        when(accountMapper.convertToDto(account)).thenReturn(accountDto);
        when(accountRepository.findAll(any(Specification.class), eq(page))).thenReturn(accountPage);

        // When
        Page<AccountDto> result = accountService.search(accountSearchDto, page);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(accountDto, result.getContent().get(0));
        verify(accountMapper).convertToDto(account);
        //verify(accountRepository).findAll(any(Specification.class), eq(page));
    }*/
}

