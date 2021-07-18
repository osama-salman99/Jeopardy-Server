package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class PlayerNotHostException extends RuntimeException {
	public PlayerNotHostException(String message) {
		super(message);
	}
}
