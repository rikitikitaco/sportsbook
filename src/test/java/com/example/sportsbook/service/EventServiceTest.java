package com.example.sportsbook.service;

import com.example.sportsbook.exception.EventNotFoundException;
import com.example.sportsbook.model.Event;
import com.example.sportsbook.persistence.MongoEventRepository;
import com.example.sportsbook.request.CreateEventRequest;
import com.example.sportsbook.request.UpdateEventRequest;
import com.example.sportsbook.websocket.SocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private MongoEventRepository mongoEventRepository;

    @Mock
    private SocketHandler socketHandler;

    @InjectMocks
    private EventService eventService;

    @Captor
    ArgumentCaptor<Event> eventCaptor;

    private final CreateEventRequest createEventRequest = new CreateEventRequest("eventName", "score");
    private final UpdateEventRequest updateEventRequest = new UpdateEventRequest("newScore");

    @Test
    public void shouldCreateEventInMongoAndNotifySocketHandler() throws JsonProcessingException {
        Event savedEvent = new Event("eventId", "eventName", "score");
        when(mongoEventRepository.save(any())).thenReturn(savedEvent);

        Event event = eventService.createEvent(createEventRequest);

        verify(mongoEventRepository).save(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getEventId()).isNotBlank();
        assertThat(eventCaptor.getValue().getEventName()).isEqualTo("eventName");
        assertThat(eventCaptor.getValue().getScore()).isEqualTo("score");

        assertThat(event.getEventId()).isEqualTo("eventId");
        assertThat(event.getEventName()).isEqualTo("eventName");
        assertThat(event.getScore()).isEqualTo("score");

        verify(socketHandler).notify(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getEventId()).isNotBlank();
        assertThat(eventCaptor.getValue().getEventName()).isEqualTo("eventName");
        assertThat(eventCaptor.getValue().getScore()).isEqualTo("score");

    }

    @Test
    public void shouldGetEventFromMongoRepository() {
        Event savedEvent = new Event("eventId", "eventName", "score");
        when(mongoEventRepository.findByEventId(any())).thenReturn(Optional.of(savedEvent));

        Optional<Event> event = eventService.getEvent("eventId");

        verify(mongoEventRepository).findByEventId("eventId");
        assertThat(event.get().getEventId()).isEqualTo("eventId");
        assertThat(event.get().getEventName()).isEqualTo("eventName");
        assertThat(event.get().getScore()).isEqualTo("score");

    }

    @Test
    public void shouldHandleEventDoesntExist() {
        when(mongoEventRepository.findByEventId(any())).thenReturn(Optional.empty());

        Optional<Event> event = eventService.getEvent("eventId");

        assertThat(event).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldCallUpdateEventAndNotifySocketHandler() throws JsonProcessingException {
        Event savedEvent = new Event("eventId", "eventName", "score");
        when(mongoEventRepository.save(any())).thenReturn(savedEvent);
        when(mongoEventRepository.findByEventId(any())).thenReturn(Optional.of(savedEvent));

        eventService.updateEvent(updateEventRequest, "eventId");

        verify(mongoEventRepository).findByEventId("eventId");

        verify(mongoEventRepository).save(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getEventId()).isNotBlank();
        assertThat(eventCaptor.getValue().getEventName()).isEqualTo("eventName");
        assertThat(eventCaptor.getValue().getScore()).isEqualTo("newScore");

        verify(socketHandler).notify(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getEventId()).isNotBlank();
        assertThat(eventCaptor.getValue().getEventName()).isEqualTo("eventName");
        assertThat(eventCaptor.getValue().getScore()).isEqualTo("newScore");
    }

    @Test
    public void shouldThrowWhenEventDoesntExistWhenUpdatingEvent() {
        when(mongoEventRepository.findByEventId(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () ->
                eventService.updateEvent(updateEventRequest, "eventId"));

        verify(mongoEventRepository).findByEventId("eventId");
        verifyNoMoreInteractions(mongoEventRepository);
        assertThat(exception.getMessage()).isEqualTo("Event not found");
    }

}