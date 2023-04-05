package ru.skillbox.diplom.group33.social.service.controller.account;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.skillbox.diplom.group33.social.service.config.AbstractIntegrationTest;
import ru.skillbox.diplom.group33.social.service.config.RestResponsePage;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.dto.account.AccountDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.skillbox.diplom.group33.social.service.config.SourceDataFactory.*;

class AccountControllerImplTest extends AbstractIntegrationTest {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Test
    @Order(1)
    void getMe() {
        ResponseEntity<AccountDto> response = getTemplate()
                .getForEntity("/api/v1/account/me", AccountDto.class);
        assertEquals(Objects.requireNonNull(response.getBody()).getFirstName(), TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getLastName(), TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getEmail(), TEST_EMAIL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    void search() {
        Map<String, String> params = new HashMap<>();
        params.put("size", "20");
        params.put("firstName:", TEST_NAME);

        ResponseEntity<RestResponsePage<AccountDto>> response = getTemplate()
                .exchange("/api/v1/account/search", HttpMethod.GET, new HttpEntity<>(""),
                ParameterizedTypeReference.forType(TypeUtils.parameterize(RestResponsePage.class, AccountDto.class)), params);
        Objects.requireNonNull(response.getBody()).forEach(accountDto -> {
            assertEquals(accountDto.getEmail(), TEST_EMAIL);
            assertEquals(accountDto.getFirstName(), TEST_NAME);
            assertEquals(accountDto.getLastName(), TEST_NAME);
        });
    }

    @Test
    @Order(3)
    void getById() {
        ResponseEntity<AccountDto> response = getTemplate()
                .getForEntity("/api/v1/account/{id}", AccountDto.class,
                        tokenProvider.getUserId(getAccessToken()));
        assertEquals(Objects.requireNonNull(response.getBody()).getFirstName(), TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getLastName(), TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getEmail(), TEST_EMAIL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(4)
    void update() {
        AccountDto accountDto = getTemplate()
                .getForEntity("/api/v1/account/{id}", AccountDto.class,
                        tokenProvider.getUserId(getAccessToken())).getBody();
        assert accountDto != null;
        accountDto.setFirstName(UPDATE_TEST_NAME);
        accountDto.setLastName(UPDATE_TEST_NAME);
        ResponseEntity<AccountDto> response = getTemplate()
                .exchange("/api/v1/account/me", HttpMethod.PUT,
                        new HttpEntity<>(accountDto), AccountDto.class);
        assertEquals(Objects.requireNonNull(response.getBody()).getFirstName(), UPDATE_TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getLastName(), UPDATE_TEST_NAME);
        assertEquals(Objects.requireNonNull(response.getBody()).getEmail(), TEST_EMAIL);
    }
}