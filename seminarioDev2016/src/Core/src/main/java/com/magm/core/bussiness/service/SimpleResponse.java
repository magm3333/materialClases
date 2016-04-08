package com.magm.core.bussiness.service;

import java.io.Serializable;

public class SimpleResponse implements Serializable {
	private static final long serialVersionUID = 6229322065137629670L;

	private int code;

	private String message;
	public SimpleResponse() {
	}

	public SimpleResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
