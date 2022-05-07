package com.wellsfargo.training.test.exception;

public class InvalidDataException extends RuntimeException {
	
	private static final long serialVersionUID = -6196864914343918387L;

	public InvalidDataException(String message) {
		super(message);
	}
}
