package ru.skillbox.diplom.group33.social.service.config.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.dto.dialog.message.MessageDto;
import ru.skillbox.diplom.group33.social.service.dto.dialog.messageShortDto.MessageShortDto;
import ru.skillbox.diplom.group33.social.service.dto.streaming.StreamingMessageDto;
import ru.skillbox.diplom.group33.social.service.mapper.dialog.message.MessageMapper;
import ru.skillbox.diplom.group33.social.service.service.account.AccountService;
import ru.skillbox.diplom.group33.social.service.service.dialog.DialogService;
import ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil.containsSession;
import static ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil.findSession;


@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {

    private final JwtTokenProvider tokenProvider;
    private final KafkaTemplate<String, Object> kafkaDialogTemplate;
    private final ObjectMapper objectMapper;
    private final AccountService accountService;
    private final DialogService dialogService;
    private final MessageMapper messageMapper;
    @Value(value = "${topic.names.message}")
    private String topic;


    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        log.info("SocketTextHandler handle: {}", message);
        super.handleMessage(session, message);
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        log.info("SocketTextHandler established: {}", session);
        WebSocketUtil.addSession(getFromJwtUserId(session), session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("SocketTextHandler closed: {}", session);
        WebSocketUtil.deleteSession(getFromJwtUserId(session));
    }

    private Long getFromJwtUserId(WebSocketSession session) {
        String jwt = Objects.requireNonNull(session.getHandshakeHeaders()
                .get("cookie")).get(0).substring(4);
        return tokenProvider.getUserId(jwt);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        if (jsonNode.get("type").textValue().equals("MESSAGE")) {
            StreamingMessageDto streamingMessageDto = objectMapper.readValue(payload, StreamingMessageDto.class);

            MessageDto messageDto = objectMapper.readValue(jsonNode.get("data").toString(), MessageDto.class);
            MessageShortDto messageShortDto = dialogService.createMessage(messageDto);

            streamingMessageDto.setData(objectMapper.convertValue(messageShortDto, HashMap.class));
            sandMessage(streamingMessageDto);
        }
    }

    @KafkaListener(topics = "${topic.names.message}")
    public void sendToSocketHandler(StreamingMessageDto<MessageShortDto> streamingMessageDto) throws IOException {
        log.info("In SocketTextHandler sendToSocketHandler: received message - {}", streamingMessageDto);
        if (streamingMessageDto != null && containsSession(streamingMessageDto.getAccountId())) {
            findSession(streamingMessageDto.getAccountId())
                    .sendMessage(new TextMessage(objectMapper.writeValueAsBytes(streamingMessageDto)));
            log.info("In SocketTextHandler sendToSocketHandler: to user with id - {}, send message to websocket - {}",
                    streamingMessageDto.getAccountId(), streamingMessageDto);
        }
    }

    private void sandMessage(StreamingMessageDto<MessageShortDto> streamingMessageDto) {
        kafkaDialogTemplate.send(topic, streamingMessageDto);
    }
}



