package com.es.phoneshop.validator;

import com.es.phoneshop.exception.ValidationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.es.phoneshop.enums.ProductPageState.*;

public class Validator {

	public static Long validatingId(String productId) {
		if(productId == null || "".equals(productId)) {
			throw new ValidationException("Id is null");
		}
		try {
			return Long.valueOf(productId.substring(1));
		} catch(NumberFormatException ex) {
			throw new ValidationException("Cannot parse id to Long");
		}
	}
	
	public static int parsingQuantity(String quantity, Locale locale) {
		Locale bufLocale = (locale == null) ? Locale.ENGLISH : locale;
		
		if(quantity == null || "".equals(quantity)) {
			throw new ValidationException(IS_EMPTY.toString().toLowerCase());
		}
		if(quantity.startsWith("-")){
			throw new ValidationException(NEGATIVE_VALUE.toString().toLowerCase());
		}
		if(!quantity.matches("[0-9]+")){
			throw new ValidationException(NOT_A_NUMBER.toString().toLowerCase());
		}
		try {
			NumberFormat format = NumberFormat.getInstance(bufLocale);
			return format.parse(quantity).intValue();
		} catch(ParseException | NumberFormatException ex) {
			throw new ValidationException(NOT_A_NUMBER.toString().toLowerCase());
		}
		
	}
}
