package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid value for car rating")
public class InvalidCarRatingException extends GenericException {

    private static final long serialVersionUID = 3859780152447089226L;

    public InvalidCarRatingException(String message) {
	super(message);
    }
}