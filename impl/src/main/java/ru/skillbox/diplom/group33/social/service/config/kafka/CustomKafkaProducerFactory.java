package ru.skillbox.diplom.group33.social.service.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static ru.skillbox.diplom.group33.social.service.config.kafka.CustomKafkaAdminFactory.JSON_TYPES_MAPPINGS;


@EnableKafka
@Configuration
public class CustomKafkaProducerFactory {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> propertiesFactory = new HashMap<>();
        propertiesFactory.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        propertiesFactory.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        propertiesFactory.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        propertiesFactory.put(JsonSerializer.TYPE_MAPPINGS, JSON_TYPES_MAPPINGS);
        return new DefaultKafkaProducerFactory<>(propertiesFactory);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
