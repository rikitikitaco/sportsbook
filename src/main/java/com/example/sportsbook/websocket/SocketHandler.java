package com.example.sportsbook.websocket;

import com.example.sportsbook.model.Event;
import com.example.sportsbook.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final Logger log = LoggerFactory.getLogger(EventService.class);

    public SocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Client connected to websocket. Session ID: " + session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("Client disconnected from websocket. Session ID: " + session.getId());
        sessions.remove(session);
    }

    public void notify(Event event) throws JsonProcessingException {
        UpdatedScoreMessage updatedScoreMessage = new UpdatedScoreMessage(event.getEventId(), event.getScore());
        TextMessage messageToSend = new TextMessage(objectMapper.writeValueAsString(updatedScoreMessage));
        for (WebSocketSession session : sessions)
            try {
                session.sendMessage(messageToSend);
            } catch (IOException e) {
                log.warn("Unexpected error when sending websocket message: " + e.getMessage());
            }
    }
}
