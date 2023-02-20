package ru.skillbox.diplom.group33.social.service.config.socket;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group33.social.service.config.security.JwtTokenProvider;
import ru.skillbox.diplom.group33.social.service.utils.socket.WebSocketUtil;

import java.util.HashSet;
import java.util.Objects;


@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {

    private final HashSet<WebSocketSession> sessions;
    private final JwtTokenProvider tokenProvider;

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
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("SocketTextHandler closed: {}", session);
        WebSocketUtil.deleteSession(getFromJwtUserId(session));
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    private Long getFromJwtUserId(WebSocketSession session) {
        String jwt = Objects.requireNonNull(session.getHandshakeHeaders()
                .get("cookie")).get(0).substring(4);
        return tokenProvider.getUserId(jwt);
    }
}



