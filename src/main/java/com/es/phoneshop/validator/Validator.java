package com.es.phoneshop.validator;

import com.es.phoneshop.enums.CartAddingState;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.exception.PersonalInfoParsingException;
import com.es.phoneshop.exception.ValidationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.Locale;

import static com.es.phoneshop.constant.ConstantStrings.*;
import static com.es.phoneshop.enums.CartAddingState.*;

public class Validator {

	public static Long validatingId(String productId) {
		if(productId == null || "".equals(productId)) {
			throw new IllegalArgumentException("Id is null");
		}
		try {
			return Long.valueOf(productId);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Cannot parse id to Long");
		}
	}
	
	public static int parsingQuantity(String quantity, Locale locale) {
		Locale bufLocale = (locale == null) ? Locale.ENGLISH : locale;
		
		if(quantity == null || "".equals(quantity)) {
			throw new ValidationException(IS_EMPTY.toString().toLowerCase());
		}
		if(quantity.startsWith("-")){
			throw new ValidationException(CartAddingState.NEGATIVE_VALUE.toString().toLowerCase());
		}
		if(!quantity.matches("[0-9]+")){
			throw new ValidationException(CartAddingState.NOT_A_NUMBER.toString().toLowerCase());
		}
		try {
			NumberFormat format = NumberFormat.getInstance(bufLocale);
			return format.parse(quantity).intValue();
		} catch(ParseException | NumberFormatException ex) {
			throw new ValidationException(CartAddingState.NOT_A_NUMBER.toString().toLowerCase());
		}
	}

	public static String parsingPhoneNumber(String rawPhoneNumber) {
		if(rawPhoneNumber == null || rawPhoneNumber.equals("")){
			throw new PersonalInfoParsingException(PHONE_NUMBER_INCORRECT);
		}
		String template = "(\\+*)(\\d{11,15})";
		String phoneNumber = rawPhoneNumber.replaceAll("[ -]", "");
		if (phoneNumber.matches(template)) {
			return phoneNumber;
		} else {
			throw new PersonalInfoParsingException(PHONE_NUMBER_INCORRECT);
		}
	}

	public static LocalDate parsingDate(String rawDate) {
		if(rawDate == null || rawDate.equals("")){
			throw new PersonalInfoParsingException(DATE_EMPTY);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			LocalDate date = format.parse(rawDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if(date.isBefore(LocalDate.now())){
				throw new ValidationException();
			}
			return date;
		} catch (ParseException e) {
			throw new PersonalInfoParsingException(DATE_INCORRECT, e);
		} catch (ValidationException e) {
			throw new PersonalInfoParsingException(DATE_WRONG, e);
		}
	}

	public static PaymentMethodType parsingPaymentType(String rawType){
		PaymentMethodType type = PaymentTypeResolver.GetTypeFromMessage(rawType);
		if(type == null){
			throw new PersonalInfoParsingException(PAYMENT_TYPE_INCORRECT);
		}
		return type;
	}

}
