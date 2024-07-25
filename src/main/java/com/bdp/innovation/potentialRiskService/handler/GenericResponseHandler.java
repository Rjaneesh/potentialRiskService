package com.bdp.innovation.potentialRiskService.handler;


import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Data;


@Data
public class GenericResponseHandler {

	private static final String DATA_CONST = "data";
	private static final String STATUS_CODE_CONST = "status";
	private static final String MESSAGE_CONST = "message";

	private final Object data;
	private final HttpStatus status;
	private final String message;

	private GenericResponseHandler(final Builder builder) {
		this.data = builder.data;
		this.message = builder.message;
		this.status = builder.status;
	}

	public static class Builder {
		private Object data;
		private HttpStatus status;
		private String message;

		public Builder setData(final Object data) {
			this.data = data;
			return this;
		}

		public Builder setStatus(final HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder setMessage(final String message) {
			this.message = message;
			return this;
		}

		public ResponseEntity<Map<String, Object>> create() {
			final GenericResponseHandler handler = new GenericResponseHandler(this);
			final Map<String, Object> responseMap = new LinkedHashMap<>(4);
			responseMap.put(STATUS_CODE_CONST, handler.status.value());
			responseMap.put(MESSAGE_CONST, handler.message);
			if (handler.data != null) {
				responseMap.put(DATA_CONST, handler.data);
			}
			return new ResponseEntity<>(responseMap, handler.status);
		}
	}
}
