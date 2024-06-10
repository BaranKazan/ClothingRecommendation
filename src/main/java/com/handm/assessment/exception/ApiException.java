package com.handm.assessment.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final HttpStatus httpStatus;
    private final String message;
    private final ZonedDateTime timeStamp;

    public ApiException(HttpStatus httpStatus, String message, ZonedDateTime timeStamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}
