package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Driver with OFFLINE status cannot select a car")
public class IncorrectStatusException extends GenericException {

    private static final long serialVersionUID = 3863782622057427742L;

    public IncorrectStatusException(String message) {
	super(message);
    }
}