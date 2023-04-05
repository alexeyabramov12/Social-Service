package ru.skillbox.diplom.group33.social.service.config.kafka;

import io.github.embeddedkafka.EmbeddedKafka;
import io.github.embeddedkafka.EmbeddedKafkaConfig;
import org.junit.After;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Configuration
public class EmbeddedKafkaConfiguration {

    private final EmbeddedKafkaConfig config = EmbeddedKafkaConfig.defaultConfig();

    @Bean
    public void start() {
        EmbeddedKafka.start(config);
    }

    @After
    public void stop() {
        EmbeddedKafka.stop();
    }
}
