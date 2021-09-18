package com.es.phoneshop.exception;

public class WrongAttributeValueException extends RuntimeException{
	
	private int exceptionCode;
	
	public WrongAttributeValueException(String message, int exceptionCode) {
		super(message);
		this.exceptionCode = exceptionCode;
	}

	public WrongAttributeValueException(String message) {
		super(message);
	}

	public WrongAttributeValueException() {
	}

	public int getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
}
