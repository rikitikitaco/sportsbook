package com.example.sportsbook.IT;

import com.example.sportsbook.model.Event;
import com.example.sportsbook.websocket.UpdatedScoreMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketIntegrationTests {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CREATE_EVENT_STRING = "{\"eventName\": \"eventName\", \"score\": \"score\"}";
    private final Event testEvent = new Event("eventId", "eventName", "score");

    @Test
    public void sendsMessageWhenEventIsCreated() throws Exception {
        String uri = "ws://localhost:" + serverPort + "/websocket";

        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        TestWebSocketHandler testWebSocketHandler = new TestWebSocketHandler();
        webSocketClient.doHandshake(testWebSocketHandler, new WebSocketHttpHeaders(), URI.create(uri)).get();


        MvcResult mvcResult = mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_EVENT_STRING))
                .andExpect(status().isOk())
                .andReturn();

        Event returnedEvent = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Event.class);
        assertThat(testWebSocketHandler.textMessages).hasSize(1);
        UpdatedScoreMessage updatedScoreMessage = objectMapper.readValue(testWebSocketHandler.textMessages.get(0), UpdatedScoreMessage.class);
        assertThat(updatedScoreMessage.getScore()).isEqualTo("score");
        assertThat(updatedScoreMessage.getEventId()).isEqualTo(returnedEvent.getEventId());
    }

    @Test
    public void sendsMessageWhenEventIsCreated(@Autowired MongoTemplate mongoTemplate) throws Exception {
        mongoTemplate.save(testEvent);
        String uri = "ws://localhost:" + serverPort + "/websocket";

        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        TestWebSocketHandler testWebSocketHandler = new TestWebSocketHandler();
        webSocketClient.doHandshake(testWebSocketHandler, new WebSocketHttpHeaders(), URI.create(uri)).get();


        mockMvc.perform(put("/event/eventId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": \"newScore\"}"))
                .andExpect(status().isOk());

        assertThat(testWebSocketHandler.textMessages).hasSize(1);
        UpdatedScoreMessage updatedScoreMessage = objectMapper.readValue(testWebSocketHandler.textMessages.get(0), UpdatedScoreMessage.class);
        assertThat(updatedScoreMessage.getScore()).isEqualTo("newScore");
        assertThat(updatedScoreMessage.getEventId()).isEqualTo("eventId");
    }


    @AfterEach
    @BeforeEach
    public void afterEach(@Autowired MongoTemplate mongoTemplate) {
        mongoTemplate.remove(testEvent);
    }

}
