package com.example.sportsbook.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventResponseTest {

    @Test
    public void testEquals() {
        EventResponse eventResponse1 = new EventResponse("eventId", "eventName", "score");
        EventResponse eventResponse2 = new EventResponse("eventId", "eventName", "score");

        assertThat(eventResponse1).isEqualTo(eventResponse2);
    }

    @Test
    public void testHashcode() {
        EventResponse eventResponse1 = new EventResponse("eventId", "eventName", "score");
        EventResponse eventResponse2 = new EventResponse("eventId", "eventName", "score");

        int hashcode1 = eventResponse1.hashCode();
        int hashcode2 = eventResponse2.hashCode();

        assertThat(hashcode1).isEqualTo(hashcode2);
    }
}