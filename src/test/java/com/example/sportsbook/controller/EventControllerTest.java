package com.example.sportsbook.controller;


import com.example.sportsbook.exception.EventNotFoundException;
import com.example.sportsbook.model.Event;
import com.example.sportsbook.request.CreateEventRequest;
import com.example.sportsbook.response.EventResponse;
import com.example.sportsbook.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;
    private final CreateEventRequest createEventRequest = new CreateEventRequest("eventName", "score");

    @Test
    public void createEventShouldCallEventService() throws JsonProcessingException {
        when(eventService.createEvent(any())).thenReturn(new Event("eventId", "eventName", "score"));

        EventResponse eventResponse = eventController.createEvent(createEventRequest);

        verify(eventService).createEvent(createEventRequest);
        assertThat(eventResponse.getEventId()).isEqualTo("eventId");
        assertThat(eventResponse.getEventName()).isEqualTo("eventName");
        assertThat(eventResponse.getScore()).isEqualTo("score");

    }

    @Test
    public void getEventShouldCallEventService() {
        when(eventService.getEvent(any())).thenReturn(Optional.of(new Event("eventId", "eventName", "score")));

        EventResponse eventResponse = eventController.getEvent("eventId");

        verify(eventService).getEvent("eventId");
        assertThat(eventResponse.getEventId()).isEqualTo("eventId");
        assertThat(eventResponse.getEventName()).isEqualTo("eventName");
        assertThat(eventResponse.getScore()).isEqualTo("score");

    }

    @Test
    public void returns404WhenNoEventFound() {
        when(eventService.getEvent(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(EventNotFoundException.class, () ->
                eventController.getEvent("eventId"));

        verify(eventService).getEvent("eventId");
        assertThat(exception.getMessage()).isEqualTo("Event not found");

    }

}