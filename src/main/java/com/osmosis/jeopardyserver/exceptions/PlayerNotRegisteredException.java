package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class PlayerNotRegisteredException extends RuntimeException{
	public PlayerNotRegisteredException(String message) {
		super(message);
	}
}
