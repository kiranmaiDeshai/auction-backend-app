package com.wellsfargo.training.test.exception;

public class BidNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5167844462708688807L;

	public BidNotFoundException(String message) {
		super(message);
	}

}
