package fr.eql.aec.exception;

import java.util.ArrayList;
import java.util.List;

public class NotValidObjectException extends AecServiceException {
	private static final long serialVersionUID = 1L;
	public NotValidObjectException() {
		new ArrayList<String>();
	}
	
	public NotValidObjectException(List<String> errors) {
	}
}