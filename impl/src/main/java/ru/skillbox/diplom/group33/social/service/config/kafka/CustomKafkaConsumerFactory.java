package ru.skillbox.diplom.group33.social.service.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationDto;
import ru.skillbox.diplom.group33.social.service.dto.notification.NotificationInputDto;
import ru.skillbox.diplom.group33.social.service.dto.streaming.StreamingMessageDto;

import java.util.HashMap;
import java.util.Map;

import static ru.skillbox.diplom.group33.social.service.config.kafka.CustomKafkaAdminFactory.*;


@EnableKafka
@Configuration
public class CustomKafkaConsumerFactory {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Object> consumerfactory() {
        Map<String, Object> propertiesFactory = new HashMap<>();
        propertiesFactory.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        propertiesFactory.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propertiesFactory.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        propertiesFactory.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propertiesFactory.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(propertiesFactory);
    }

    @Bean
    public RecordMessageConverter typeMassageConverter() {
        StringJsonMessageConverter converter = new StringJsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("*");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put(NOTIFICATION_INPUT_DTO_KEY, NotificationInputDto.class);
        mappings.put(NOTIFICATION_DTO_KEY, NotificationDto.class);
        mappings.put(MESSAGE_DTO_KEY, MessageDto.class);
        mappings.put(STREAMING_MESSAGE_DTO, StreamingMessageDto.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerfactory());
        factory.setMessageConverter(typeMassageConverter());
        return factory;
    }
}
