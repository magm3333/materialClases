package com.magm.core.bussiness.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -7169270944680916375L;

	public ServiceException() {

	}

	public ServiceException(String message) {
		super(message);

	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);

	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ServiceException(Throwable cause) {
		super(cause);

	}

}
