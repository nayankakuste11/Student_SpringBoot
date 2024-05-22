package com.qsp.student_springboot.exception;

public class NoDataAvaliableException extends RuntimeException{

private String message;
	
	public NoDataAvaliableException(String message) {
			this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
