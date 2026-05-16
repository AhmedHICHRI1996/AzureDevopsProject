package fr.eql.aec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST) 
public class IllegalOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IllegalOperationException() {}

	public IllegalOperationException(String message) {
		super(message);
	}

	public IllegalOperationException(Throwable cause) {
		super(cause);
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}