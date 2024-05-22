package com.qsp.student_springboot.exception;

public class EmailNotFoudException extends RuntimeException{

	private String message;
	
	public EmailNotFoudException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
