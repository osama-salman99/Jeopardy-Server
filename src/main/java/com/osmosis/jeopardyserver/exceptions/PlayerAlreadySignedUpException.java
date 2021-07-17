package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class PlayerAlreadySignedUpException extends RuntimeException{
	public PlayerAlreadySignedUpException(String message) {
		super(message);
	}
}
