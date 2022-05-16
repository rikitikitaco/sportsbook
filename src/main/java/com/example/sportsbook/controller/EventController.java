package com.example.sportsbook.controller;

import com.example.sportsbook.exception.EventNotFoundException;
import com.example.sportsbook.model.Event;
import com.example.sportsbook.request.CreateEventRequest;
import com.example.sportsbook.request.UpdateEventRequest;
import com.example.sportsbook.response.EventResponse;
import com.example.sportsbook.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(
            value = "/event",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    @ResponseBody
    public EventResponse createEvent(@RequestBody CreateEventRequest createEventRequest) {
        Event event = eventService.createEvent(createEventRequest);
        return new EventResponse(
                event.getEventId(),
                event.getEventName(),
                event.getScore()
        );
    }

    @RequestMapping(
            value = "/event/{eventId}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ResponseBody
    public EventResponse getEvent(@PathVariable String eventId) {
        Event event = eventService.getEvent(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        return new EventResponse(
                event.getEventId(),
                event.getEventName(),
                event.getScore()
        );
    }

    @RequestMapping(
            value = "/event/{eventId}",
            method = RequestMethod.PUT,
            consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public void updateEvent(@PathVariable String eventId, @RequestBody UpdateEventRequest updateEventRequest) {
        eventService.updateEvent(updateEventRequest, eventId);
    }
}
