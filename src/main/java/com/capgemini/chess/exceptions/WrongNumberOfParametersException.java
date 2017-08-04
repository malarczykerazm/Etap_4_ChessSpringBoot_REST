package com.capgemini.chess.exceptions;

public class WrongNumberOfParametersException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public WrongNumberOfParametersException(String message) {
		super(message);
	}
}
