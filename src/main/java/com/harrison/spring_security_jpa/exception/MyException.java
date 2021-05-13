package com.harrison.spring_security_jpa.exception;

import org.springframework.http.HttpStatus;

public class MyException extends Exception {
	private HttpStatus status;

	public MyException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
