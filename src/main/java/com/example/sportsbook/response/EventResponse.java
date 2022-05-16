package com.example.sportsbook.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResponse that = (EventResponse) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(eventName, that.eventName) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, score);
    }
}
