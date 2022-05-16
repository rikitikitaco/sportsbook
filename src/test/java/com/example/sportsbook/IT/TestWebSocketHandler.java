package com.example.sportsbook.IT;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class TestWebSocketHandler extends TextWebSocketHandler {

    public List<String> textMessages = new ArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        textMessages.add(message.getPayload());
    }

}
