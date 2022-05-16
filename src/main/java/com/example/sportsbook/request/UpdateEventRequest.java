package com.example.sportsbook.request;

import java.util.Objects;

public class UpdateEventRequest {

    public UpdateEventRequest() {}
    public UpdateEventRequest(String score) {
        this.score = score;
    }

    private String score;

    public String getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateEventRequest that = (UpdateEventRequest) o;
        return Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
