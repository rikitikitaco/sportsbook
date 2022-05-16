package com.example.sportsbook.request;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateEventRequestTest {

    @Test
    public void testEquals() {
        CreateEventRequest createEventRequest1 = new CreateEventRequest("eventName", "score");
        CreateEventRequest createEventRequest2 = new CreateEventRequest("eventName", "score");

        assertThat(createEventRequest1).isEqualTo(createEventRequest2);
    }

    @Test
    public void testHashcode() {
        CreateEventRequest createEventRequest1 = new CreateEventRequest("eventName", "score");
        CreateEventRequest createEventRequest2 = new CreateEventRequest("eventName", "score");

        int hashcode1 = createEventRequest1.hashCode();
        int hashcode2 = createEventRequest2.hashCode();

        assertThat(hashcode1).isEqualTo(hashcode2);
    }
}