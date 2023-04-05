package ru.skillbox.diplom.group33.social.service.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.skillbox.diplom.group33.social.service.dto.auth.AuthenticateResponseDto;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;
import ru.skillbox.diplom.group33.social.service.model.captcha.Captcha;
import ru.skillbox.diplom.group33.social.service.repository.captcha.CaptchaRepository;

import java.util.Objects;
import java.util.UUID;

import static ru.skillbox.diplom.group33.social.service.config.SourceDataFactory.authenticateDto;
import static ru.skillbox.diplom.group33.social.service.config.SourceDataFactory.registrationDto;

@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private CaptchaRepository captchaRepository;
    private String accessToken;

    @BeforeEach
    void init() {
        ResponseEntity<CaptchaDto> responseCaptcha = template
                .getForEntity("/api/v1/auth/captcha", CaptchaDto.class);
        Captcha captcha = captchaRepository.findById(UUID.fromString(
                Objects.requireNonNull(responseCaptcha.getBody()).getSecret())).orElseThrow();

        template.postForEntity("/api/v1/auth/register",
                registrationDto(captcha, responseCaptcha.getBody()),
                HttpStatus.class);

        accessToken = Objects.requireNonNull(template
                .postForEntity("/api/v1/auth/login", authenticateDto(),
                        AuthenticateResponseDto.class).getBody()).getAccessToken();

        template.getRestTemplate().getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().setBearerAuth(accessToken);
                    request.getHeaders().set("Cookie", "jwt=" + accessToken);
                    return execution.execute(request, body);
                });
    }

    public TestRestTemplate getTemplate() {
        return template;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
