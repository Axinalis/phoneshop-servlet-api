package com.es.phoneshop.validator;

import com.es.phoneshop.exception.IllegalPathSegmentException;

public class Validator {

	public static Long validadingId(String productId) {
		
		if(productId == null) {
			throw new IllegalPathSegmentException("Id is null");
		}
		try {
			return Long.valueOf(productId.substring(1));
		} catch(NumberFormatException ex) {
			throw new IllegalPathSegmentException("Cannot parse id to Long");
		}
		
	}
	
	public static int validatingQuantity(String quantity) {
		
		if(quantity == null) {
			throw new IllegalPathSegmentException("Value is null");
		}
		try {
			return Integer.valueOf(quantity);
		} catch(NumberFormatException ex) {
			throw new IllegalPathSegmentException("Cannot parse string quantity to int");
		}
		
	}
	
}
