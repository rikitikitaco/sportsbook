package com.example.sportsbook.IT;

import com.example.sportsbook.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
public class EventIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String CREATE_EVENT_STRING = "{\"eventName\": \"eventName\", \"score\": \"score\"}";
    private static final String EVENT_STRING = "{\"eventId\": \"eventId\", \"eventName\": \"eventName\", \"score\": \"score\"}";
    private final Event testEvent = new Event("eventId", "eventName", "score");

    @Test
    public void canCreateEvent(@Autowired MongoTemplate mongoTemplate) throws Exception {


        MvcResult result = mockMvc.perform(post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_EVENT_STRING))
                .andExpect(status().isOk())
                .andReturn();

        Event returnedEvent = objectMapper.readValue(result.getResponse().getContentAsString(), Event.class);
        List<Event> events = mongoTemplate.find(new Query(Criteria.where("eventId").is(returnedEvent.getEventId())), Event.class);
        Event persistedEvent = events.get(0);

        assertThat(returnedEvent.getEventName()).isEqualTo("eventName");
        assertThat(returnedEvent.getScore()).isEqualTo("score");
        assertThat(events.get(0).getEventId()).isEqualTo(persistedEvent.getEventId());
        assertThat(events.get(0).getEventName()).isEqualTo("eventName");
        assertThat(events.get(0).getScore()).isEqualTo("score");

    }

    @Test
    public void canGetEvent(@Autowired MongoTemplate mongoTemplate) throws Exception {
        mongoTemplate.save(testEvent);

        MvcResult result = mockMvc.perform(get("/event/eventId"))
                .andExpect(status().isOk())
                .andExpect(content().json(EVENT_STRING))
                .andReturn();

        Event returnedEvent = objectMapper.readValue(result.getResponse().getContentAsString(), Event.class);
        assertThat(returnedEvent.getEventId()).isEqualTo("eventId");
        assertThat(returnedEvent.getEventName()).isEqualTo("eventName");
        assertThat(returnedEvent.getScore()).isEqualTo("score");
    }

    @Test
    public void canUpdateEvent(@Autowired MongoTemplate mongoTemplate) throws Exception {
        mongoTemplate.save(testEvent);

        mockMvc.perform(put("/event/eventId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\": \"newScore\"}"))
                .andExpect(status().isOk());

        List<Event> events = mongoTemplate.find(new Query(Criteria.where("eventId").is("eventId")), Event.class);
        assertThat(events.get(0).getScore()).isEqualTo("newScore");


    }
    
    @AfterEach
    @BeforeEach
    public void afterEach(@Autowired MongoTemplate mongoTemplate) {
        mongoTemplate.remove(testEvent);
    }
}
