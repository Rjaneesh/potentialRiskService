package com.bdp.innovation.potentialRiskService.exceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied.", code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 4671758050870673270L;

	private String errorCode;

	public ForbiddenException() {
		super();
	}

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}

	public String getErrorMessage() {
		return null;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
