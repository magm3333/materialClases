package com.magm.core.exception;

public class NotFoundException extends Exception {


	private static final long serialVersionUID = 5804489583959955873L;

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
