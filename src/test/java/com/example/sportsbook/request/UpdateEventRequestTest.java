package com.example.sportsbook.request;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateEventRequestTest {

    @Test
    public void testEquals() {
        UpdateEventRequest updateEventRequest1 = new UpdateEventRequest("score");
        UpdateEventRequest updateEventRequest2 = new UpdateEventRequest("score");

        assertThat(updateEventRequest1).isEqualTo(updateEventRequest2);
    }

    @Test
    public void testHashcode() {
        UpdateEventRequest updateEventRequest1 = new UpdateEventRequest("score");
        UpdateEventRequest updateEventRequest2 = new UpdateEventRequest("score");

        int hashcode1 = updateEventRequest1.hashCode();
        int hashcode2 = updateEventRequest2.hashCode();

        assertThat(hashcode1).isEqualTo(hashcode2);
    }
}