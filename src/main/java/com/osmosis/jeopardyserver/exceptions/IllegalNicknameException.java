package com.osmosis.jeopardyserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalNicknameException extends RuntimeException {
	public IllegalNicknameException(String message) {
		super(message);
	}
}
