package com.example.sportsbook.request;

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

}
