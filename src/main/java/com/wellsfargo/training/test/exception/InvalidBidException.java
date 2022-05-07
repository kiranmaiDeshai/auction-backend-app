package com.wellsfargo.training.test.exception;

public class InvalidBidException extends RuntimeException {
	
	private static final long serialVersionUID = 82625287698734045L;

	public InvalidBidException(String message) {
		super(message);
	}
}
