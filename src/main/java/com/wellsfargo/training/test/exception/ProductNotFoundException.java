package com.wellsfargo.training.test.exception;

public class ProductNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4543476087447852855L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}
