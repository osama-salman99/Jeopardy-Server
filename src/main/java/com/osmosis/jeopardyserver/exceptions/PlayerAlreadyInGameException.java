package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class PlayerAlreadyInGameException extends RuntimeException {
	public PlayerAlreadyInGameException(String message) {
		super(message);
	}
}
