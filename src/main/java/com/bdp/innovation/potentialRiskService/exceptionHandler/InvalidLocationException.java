package com.bdp.innovation.potentialRiskService.exceptionHandler;

public class InvalidLocationException extends RuntimeException {

	private static final long serialVersionUID = -121L;

	public InvalidLocationException() {
		super();
	}

	public InvalidLocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLocationException(String message) {
		super(message);
	}

}
