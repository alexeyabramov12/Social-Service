package ru.skillbox.diplom.group33.social.service.utils.socket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketUtil {

    private static final ConcurrentHashMap<Long, WebSocketSession> webSocketSession = new ConcurrentHashMap<>();

    public static void addSession(Long id, WebSocketSession session) {
        log.info("WebSocketUtil added session: {}", id);
        webSocketSession.put(id, session);
    }

    public static WebSocketSession findSession(Long id) {
        log.info("WebSocketUtil find session: {}", id);
        return webSocketSession.get(id);
    }

    public static boolean containsSession(Long id) {
        log.info("WebSocketUtil contains session: {}", id);
        return webSocketSession.containsKey(id);
    }

    public static void deleteSession(Long id) {
        log.info("WebSocketUtil delete session: {}", id);
        webSocketSession.remove(id);
    }
}
