package com.capgemini.chess.exceptions;

public class UserValidationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public UserValidationException(String message) {
		super(message);
	}

}
