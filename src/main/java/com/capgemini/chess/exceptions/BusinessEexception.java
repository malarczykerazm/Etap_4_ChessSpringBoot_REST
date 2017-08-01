package com.capgemini.chess.exceptions;

public abstract class BusinessEexception extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessEexception(String message) {
		super(message);
	}
}
