package com.es.phoneshop.validator;

import com.es.phoneshop.enums.ProductPageState;
import com.es.phoneshop.exception.IllegalPathSegmentException;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;

public class Validator {

	public static Long validatingId(String productId) {
		
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
		
		if(quantity == null || "".equals(quantity)) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.IS_EMPTY);
		}
		try {
			return Integer.valueOf(quantity);
		} catch(NumberFormatException ex) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.NOT_A_NUMBER);
		}
		
	}
	
}
