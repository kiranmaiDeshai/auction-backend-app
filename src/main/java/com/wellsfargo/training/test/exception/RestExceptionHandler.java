package com.wellsfargo.training.test.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { InvalidDataException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException exp, WebRequest request) {
		return handleExceptionInternal(exp, "Invalid Request", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
