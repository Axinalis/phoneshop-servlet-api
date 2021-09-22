package com.es.phoneshop.validator;

import com.es.phoneshop.constant.ProductPageState;
import com.es.phoneshop.exception.IllegalPathSegmentException;
import com.es.phoneshop.exception.WrongQuantityValueOnProductPageException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Validator {

	public static Long validatingId(String productId) {
		
		if(productId == null || "".equals(productId)) {
			throw new IllegalPathSegmentException("Id is null");
		}
		try {
			return Long.valueOf(productId.substring(1));
		} catch(NumberFormatException ex) {
			throw new IllegalPathSegmentException("Cannot parse id to Long");
		}
		
	}
	
	public static int parsingQuantity(String quantity, Locale locale) {
		Locale bufLocale = (locale == null) ? Locale.ENGLISH : locale;
		
		if(quantity == null || "".equals(quantity)) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.IS_EMPTY);
		}
		try {
			NumberFormat format = NumberFormat.getInstance(locale);
			return format.parse(quantity).intValue();
		} catch(ParseException ex) {
			throw new WrongQuantityValueOnProductPageException(ProductPageState.NOT_A_NUMBER);
		}
		
	}
	
}
