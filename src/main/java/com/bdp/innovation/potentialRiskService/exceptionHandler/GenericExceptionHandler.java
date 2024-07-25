package com.bdp.innovation.potentialRiskService.exceptionHandler;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bdp.innovation.potentialRiskService.dto.ManagementResponse;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class GenericExceptionHandler {

	@Value("${spring.servlet.multipart.max-file-size}")
	private String fileSize;

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", HttpStatus.BAD_REQUEST);
		body.put("message", exception.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(ValidationException exception) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("status", HttpStatus.BAD_REQUEST);
		body.put("message", exception.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ManagementResponse> handleMaxSizeException(MaxUploadSizeExceededException exception) {
		ManagementResponse body = new ManagementResponse();
		body.setReturnCode(417);
		body.setReturnMessage("Maximum upload size exceeded. File size should be less than " + fileSize);
		return new ResponseEntity<ManagementResponse>(body, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(InvalidLocationException.class)
	public ResponseEntity<ManagementResponse> handleInvalidLocationException(InvalidLocationException exception) {
		ManagementResponse body = new ManagementResponse();
		body.setReturnCode(417);
		body.setReturnMessage(exception.getMessage());
		return new ResponseEntity<ManagementResponse>(body, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(LazyInitializationException.class)
	public ResponseEntity<ManagementResponse> handleLazyInitializationException(LazyInitializationException exception) {
		ManagementResponse body = new ManagementResponse();
		body.setReturnCode(417);
		body.setReturnMessage(exception.getMessage());
		return new ResponseEntity<ManagementResponse>(body, HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ManagementResponse> handleBaseException(BaseException exception) {
		ManagementResponse body = new ManagementResponse();
		body.setReturnCode(417);
		body.setReturnMessage(exception.getMessage());
		return new ResponseEntity<ManagementResponse>(body, HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ManagementResponse> handleBadJwtException(ExpiredJwtException exception) {
		ManagementResponse body = new ManagementResponse();
		body.setReturnCode(401);
		body.setReturnMessage(exception.getMessage());
		return new ResponseEntity<ManagementResponse>(body, HttpStatus.UNAUTHORIZED);
	}
}
