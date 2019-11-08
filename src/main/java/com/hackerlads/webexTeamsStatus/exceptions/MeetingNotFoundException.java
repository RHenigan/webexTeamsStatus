package com.hackerlads.webexTeamsStatus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MeetingNotFoundException extends RuntimeException{
    public MeetingNotFoundException(String error) {
        super(error);
    }
}
