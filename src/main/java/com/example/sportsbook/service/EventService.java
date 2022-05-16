package com.example.sportsbook.service;

import com.example.sportsbook.exception.EventNotFoundException;
import com.example.sportsbook.model.Event;
import com.example.sportsbook.persistence.MongoEventRepository;
import com.example.sportsbook.request.CreateEventRequest;
import com.example.sportsbook.request.UpdateEventRequest;
import com.example.sportsbook.websocket.SocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EventService {

    private final MongoEventRepository mongoEventRepository;
    private final SocketHandler socketHandler;
    private final Logger log = LoggerFactory.getLogger(EventService.class);

    public EventService(MongoEventRepository mongoEventRepository, SocketHandler socketHandler) {
        this.mongoEventRepository = mongoEventRepository;
        this.socketHandler = socketHandler;
    }

    public Event createEvent(final CreateEventRequest createEventRequest) throws JsonProcessingException {
        String eventId = String.valueOf(UUID.randomUUID());
        Event event = new Event(
                eventId,
                createEventRequest.getEventName(),
                createEventRequest.getScore()
        );
        log.info("Created event: " + event);
        socketHandler.notify(event);
        return mongoEventRepository.save(event);
    }

    public Optional<Event> getEvent(final String eventId) {
        return mongoEventRepository.findByEventId(eventId);
    }

    public void updateEvent(final UpdateEventRequest updateEventRequest, String eventId) throws JsonProcessingException {
        Event event = mongoEventRepository.findByEventId(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        event.setScore(updateEventRequest.getScore());
        log.info("Updated event: " + event);
        socketHandler.notify(event);
        mongoEventRepository.save(event);
    }
}
