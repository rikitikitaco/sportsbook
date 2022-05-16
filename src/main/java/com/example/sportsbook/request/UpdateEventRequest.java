package com.example.sportsbook.request;

public class UpdateEventRequest {

    public UpdateEventRequest() {}
    public UpdateEventRequest(String score) {
        this.score = score;
    }

    private String score;

    public String getScore() {
        return score;
    }

}
