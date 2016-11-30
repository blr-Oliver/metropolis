package com.metropolis.security;

public class DuplicateEmailException extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException() {
		super();
	}

	public DuplicateEmailException(String message) {
		super(message);
	}
}
