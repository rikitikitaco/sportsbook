package com.example.sportsbook.websocket;

import java.util.Objects;

public class UpdatedScoreMessage  {

    private final String score;
    private final String eventId;

    public UpdatedScoreMessage(String eventId, String score) {
        this.eventId = eventId;
        this.score = score;
    }

    public String getEventId() {
        return eventId;
    }

    public String getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatedScoreMessage that = (UpdatedScoreMessage) o;
        return Objects.equals(score, that.score) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, eventId);
    }
}
