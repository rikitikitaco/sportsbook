package com.example.sportsbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Event not found")
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String exception) {
        super(exception);
    }
}
