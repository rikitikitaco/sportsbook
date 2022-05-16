package com.example.sportsbook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "events")
public class Event {

    public Event(String eventId, String eventName, String score) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.score = score;
    }

    @Id
    private final String eventId;
    private String eventName;
    private String score;

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getScore() {
        return score;
    }
}
