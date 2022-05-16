package com.example.sportsbook.request;


import java.util.Objects;

public class CreateEventRequest {

    public CreateEventRequest(String eventName, String score) {
        this.eventName = eventName;
        this.score = score;
    }

    private final String eventName;
    private final String score;

    public String getEventName() {
        return eventName;
    }

    public String getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateEventRequest that = (CreateEventRequest) o;
        return Objects.equals(eventName, that.eventName) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, score);
    }
}
