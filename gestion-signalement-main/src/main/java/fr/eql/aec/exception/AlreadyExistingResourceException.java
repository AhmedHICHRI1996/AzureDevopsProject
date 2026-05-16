package fr.eql.aec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT) 
public class AlreadyExistingResourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "already exist exception";
	
	public AlreadyExistingResourceException() {
	}	
	public AlreadyExistingResourceException(String message) {
		super(message);
	}

	public AlreadyExistingResourceException(Throwable cause) {
		super(cause);
	}

	public AlreadyExistingResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlreadyExistingResourceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}