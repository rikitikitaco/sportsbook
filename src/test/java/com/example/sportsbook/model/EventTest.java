package com.example.sportsbook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    void testEquals() {
        Event event1 = new Event("eventId", "eventName", "score");
        Event event2 = new Event("eventId", "eventName", "score");

        assertThat(event1).isEqualTo(event2);
    }

    @Test
    void testHashCode() {
        Event event1 = new Event("eventId", "eventName", "score");
        Event event2 = new Event("eventId", "eventName", "score");

        int hashCode1 = event1.hashCode();
        int hashCode2 = event2.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    void testToString() {
        Event event = new Event("eventId", "eventName", "score");
        assertThat(event.toString()).isEqualTo("Event{eventId='eventId', eventName='eventName', score='score'}");
    }
}