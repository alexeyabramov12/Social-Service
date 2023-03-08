package ru.skillbox.diplom.group33.social.service.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CustomKafkaAdminFactory {

    protected static final String JSON_TYPES_MAPPINGS = "NotificationInputDto:ru.skillbox.diplom." +
            "group33.social.service.dto.notification.NotificationInputDto, NotificationDto:ru." +
            "skillbox.diplom.group33.social.service.dto.notification.NotificationDto, MessageShortDto:" +
            "ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto.MessageShortDto," +
            "StreamingMessageDto:ru.skillbox.diplom.group33.social.service.dto.streaming.StreamingMessageDto";

    protected static final String NOTIFICATION_INPUT_DTO_KEY = "NotificationInputDto";

    protected static final String NOTIFICATION_DTO_KEY = "NotificationDto";

    protected static final String MESSAGE_SHORT_DTO_KEY = "MessageShortDto";
    protected static final String STREAMING_MESSAGE_DTO = "StreamingMessageDto";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.names.notify}")
    private String topicNotify;

    @Value(value = "${topic.names.message}")
    private String topicMessage;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicNotify() {
        return new NewTopic(topicNotify, 1, (short) 1);
    }

    @Bean
    public NewTopic topicMessage() {
        return new NewTopic(topicMessage, 1, (short) 1);
    }
}
