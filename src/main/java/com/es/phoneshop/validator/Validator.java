package com.es.phoneshop.validator;

import com.es.phoneshop.exception.ValidationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;

import static com.es.phoneshop.constant.ProductPageState.*;

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
		try {
			if(containsSymbols(quantity)){
				throw new ValidationException(NOT_A_NUMBER.toString().toLowerCase());
			}
			NumberFormat format = NumberFormat.getInstance(bufLocale);
			return format.parse(quantity).intValue();
		} catch(ParseException | NumberFormatException ex) {
			throw new ValidationException(NOT_A_NUMBER.toString().toLowerCase());
		}
		
	}

	private static boolean containsSymbols(String quantity){
		String alphabet = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
		for(String letter: alphabet.split("\\s")){
			if(quantity.toLowerCase().contains(letter)){
				return true;
			}
		}
		return false;
	}
}
