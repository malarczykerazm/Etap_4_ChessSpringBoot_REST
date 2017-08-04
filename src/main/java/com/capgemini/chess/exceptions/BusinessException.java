package com.capgemini.chess.exceptions;

public abstract class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}
}
