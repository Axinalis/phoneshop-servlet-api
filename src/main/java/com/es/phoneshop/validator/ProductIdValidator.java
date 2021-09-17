package com.es.phoneshop.validator;

import com.es.phoneshop.exception.IdIsNotValidException;
import com.es.phoneshop.exception.ProductNotFoundException;

public class ProductIdValidator {

	public static Long validadingId(String productId) {
		
		if(productId == null) {
			throw new IdIsNotValidException("Id is null");
		}
		try {
			return Long.valueOf(productId.substring(1));
		} catch(NumberFormatException ex) {
			throw new IdIsNotValidException("Cannot parse id to Long");
		}
		
	}
	
}
