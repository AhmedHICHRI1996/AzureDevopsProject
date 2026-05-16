package fr.eql.aec.exception;

public class AecServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public AecServiceException() {}

	public AecServiceException(String message) {
		super(message);
	}

	public AecServiceException(Throwable cause) {
		super(cause);
	}

	public AecServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AecServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}