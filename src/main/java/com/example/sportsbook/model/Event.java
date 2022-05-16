package com.example.sportsbook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId) && Objects.equals(eventName, event.eventName) && Objects.equals(score, event.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, score);
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
