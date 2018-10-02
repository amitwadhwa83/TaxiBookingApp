package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated ...")
public class ConstraintsViolationException extends GenericException {

    static final long serialVersionUID = -3387516993224229948L;

    public ConstraintsViolationException(String message) {
	super(message);
    }
}
