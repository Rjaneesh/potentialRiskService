package com.bdp.innovation.potentialRiskService.exceptionHandler;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -5751984240612599676L;
	private final String errorCode;

	public BaseException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BaseException(String erroMessage) {
		super(erroMessage);
		this.errorCode = "";
	}

	public String getErrorMessage() {
		return null;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
