package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends RuntimeException {
	public SessionExpiredException(String message) {
		super(message);
	}
}
