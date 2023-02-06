package ru.skillbox.diplom.group33.social.service.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group33.social.service.dto.captcha.CaptchaDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(CaptchaDto captchaDto) {
        try {
            String message = objectMapper.writeValueAsString(captchaDto);
            kafkaTemplate.send("social-service-topic", message);
            log.info("produced messaged {}", message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
