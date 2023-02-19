package ru.skillbox.diplom.group33.social.service.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "social-service-topic")
    public void consumeMessage(String message) {
        log.info("message consumed {}", message);
        try {
            CaptchaDto captchaDto = objectMapper.readValue(message, CaptchaDto.class);
            System.out.println(captchaDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
