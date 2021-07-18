package com.osmosis.jeopardyserver.exceptions;

public class BoardFullException extends RuntimeException {
	public BoardFullException(String message) {
		super(message);
	}
}
