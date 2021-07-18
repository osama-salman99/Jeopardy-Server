package com.osmosis.jeopardyserver.exceptions;

public class CategoryFullException extends RuntimeException {
	public CategoryFullException(String message) {
		super(message);
	}
}
