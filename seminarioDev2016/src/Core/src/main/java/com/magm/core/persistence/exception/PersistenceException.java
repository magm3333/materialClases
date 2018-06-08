package com.magm.core.persistence.exception;

public class PersistenceException extends Exception {


	private static final long serialVersionUID = 5804489583959955873L;

	public PersistenceException() {
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}

}
