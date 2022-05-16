package com.example.sportsbook.websocket;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdatedScoreMessageTest {

    @Test
    public void testEquals() {
        UpdatedScoreMessage updatedScoreMessage1 = new UpdatedScoreMessage("eventId", "score");
        UpdatedScoreMessage updatedScoreMessage2 = new UpdatedScoreMessage("eventId", "score");

        assertThat(updatedScoreMessage1).isEqualTo(updatedScoreMessage2);
    }

    @Test
    public void testHashcode() {
        UpdatedScoreMessage updatedScoreMessage1 = new UpdatedScoreMessage("eventId", "score");
        UpdatedScoreMessage updatedScoreMessage2 = new UpdatedScoreMessage("eventId", "score");

        int hashcode1 = updatedScoreMessage1.hashCode();
        int hashcode2 = updatedScoreMessage2.hashCode();

        assertThat(hashcode1).isEqualTo(hashcode2);
    }
}