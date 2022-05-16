package com.example.sportsbook.response;

public class EventResponse {

    public EventResponse(String eventId, String eventName, String score) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.score = score;
    }

    private final String eventId;
    private final String eventName;
    private final String score;

    public String getEventName() {
        return eventName;
    }

    public String getScore() {
        return score;
    }

    public String getEventId() {
        return eventId;
    }
}
