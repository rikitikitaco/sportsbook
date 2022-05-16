package com.example.sportsbook.websocket;


import com.example.sportsbook.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SocketHandlerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SocketHandler socketHandler = new SocketHandler(objectMapper);

    private final Event event = new Event("eventId", "eventName", "score");
    private final UpdatedScoreMessage scoreMessage = new UpdatedScoreMessage("eventId", "score");
    private final TextMessage message = new TextMessage(objectMapper.writeValueAsString(scoreMessage));

    SocketHandlerTest() throws JsonProcessingException {
    }

    @Test
    public void shouldNotifySession() throws IOException {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        socketHandler.afterConnectionEstablished(webSocketSession);

        when(webSocketSession.isOpen()).thenReturn(true);

        socketHandler.notify(event);

        verify(webSocketSession, times(1)).sendMessage(eq(message));
    }

    @Test
    public void shouldNotNotifySessionWhenSessionConnectionIsClosed() throws IOException {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        socketHandler.afterConnectionEstablished(webSocketSession);
        socketHandler.afterConnectionClosed(webSocketSession, CloseStatus.NORMAL);

        socketHandler.notify(event);

        verify(webSocketSession, times(0)).sendMessage(any());
    }

    @Test
    public void shouldHandleErrorWhenSendingMessage() throws IOException {
        WebSocketSession webSocketSession = mock(WebSocketSession.class);
        socketHandler.afterConnectionEstablished(webSocketSession);
        doThrow(new IOException())
                .when(webSocketSession).sendMessage(any(WebSocketMessage.class));

        socketHandler.notify(event);
    }
}