package com.example.sportsbook.service;

import com.example.sportsbook.exception.EventNotFoundException;
import com.example.sportsbook.model.Event;
import com.example.sportsbook.persistence.MongoEventRepository;
import com.example.sportsbook.request.CreateEventRequest;
import com.example.sportsbook.request.UpdateEventRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EventService {

    private final MongoEventRepository mongoEventRepository;

    public EventService(MongoEventRepository mongoEventRepository) {
        this.mongoEventRepository = mongoEventRepository;
    }

    public Event createEvent(final CreateEventRequest createEventRequest) {
        String eventId = String.valueOf(UUID.randomUUID());
        Event event = new Event(
                eventId,
                createEventRequest.getEventName(),
                createEventRequest.getScore()
        );
       return mongoEventRepository.save(event);
    }

    public Optional<Event> getEvent(final String eventId) {
        return mongoEventRepository.findByEventId(eventId);
    }

    public void updateEvent(final UpdateEventRequest updateEventRequest, String eventId) {
        Event event = mongoEventRepository.findByEventId(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        event.setScore(updateEventRequest.getScore());
        mongoEventRepository.save(event);
    }
}
