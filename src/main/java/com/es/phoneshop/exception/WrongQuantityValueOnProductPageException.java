package com.es.phoneshop.exception;

import com.es.phoneshop.constant.ProductPageState;

public class WrongQuantityValueOnProductPageException extends RuntimeException{
	
	private ProductPageState state;
	
	public WrongQuantityValueOnProductPageException(String message, ProductPageState state) {
		super(message);
		this.state = state;
	}

	public WrongQuantityValueOnProductPageException(String message) {
		super(message);
	}

	public WrongQuantityValueOnProductPageException(ProductPageState state) {
		this.state = state;
	}

	public WrongQuantityValueOnProductPageException() {
	}

	public ProductPageState getState() {
		return state;
	}

	public void setState(ProductPageState state) {
		this.state = state;
	}
	
}
