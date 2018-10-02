package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find entity with given input id")
public class EntityNotFoundException extends GenericException {
    static final long serialVersionUID = -3387516993334229948L;

    public EntityNotFoundException(String message) {
	super(message);
    }
}
