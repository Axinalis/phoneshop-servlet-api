package com.es.phoneshop.validator;

import com.es.phoneshop.enums.CartAddingState;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.exception.PersonalInfoParsingException;
import com.es.phoneshop.exception.ValidationException;

import java.math.BigDecimal;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

import static com.es.phoneshop.constant.ConstantStrings.*;
import static com.es.phoneshop.enums.CartAddingState.*;

public class Validator {

	public static Long validatingId(String productId) {
		if(productId == null || "".equals(productId)) {
			throw new IllegalArgumentException(ID_IS_NULL);
		}
		try {
			return Long.valueOf(productId);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException(ID_NOT_LONG);
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
			throw new PersonalInfoParsingException(PHONE_NUMBER_IS_NULL);
		}
		String phoneNumber = rawPhoneNumber.replaceAll("[ -]", "");
		if (phoneNumber.matches(PHONE_NUMBER_TEMPLATE)) {
			return phoneNumber;
		} else {
			throw new PersonalInfoParsingException(PHONE_NUMBER_INCORRECT);
		}
	}

	public static LocalDate parsingDate(String rawDate) {
		if(rawDate == null || rawDate.equals("")){
			throw new PersonalInfoParsingException(DATE_EMPTY);
		}
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		try {
			LocalDate date = format.parse(rawDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if(date.isBefore(LocalDate.now())){
				throw new PersonalInfoParsingException(DATE_IS_BEFORE);
			}
			return date;
		} catch (ParseException e) {
			throw new PersonalInfoParsingException(DATE_INCORRECT, e);
		}
	}

	public static PaymentMethodType parsingPaymentType(String rawType){
		if(rawType == null || rawType.equals("")){
			throw new PersonalInfoParsingException(PAYMENT_TYPE_INCORRECT);
		}
		PaymentMethodType type = PaymentTypeResolver.GetTypeFromMessage(rawType);
		if(type == null){
			throw new PersonalInfoParsingException(PAYMENT_TYPE_INCORRECT);
		}
		return type;
	}

	public static BigDecimal parsingPrice(String rawPrice){
		if(rawPrice == null || rawPrice.equals("")){
			return null;
		}
		BigDecimal price = null;
		try{
			price = BigDecimal.valueOf(Double.parseDouble(rawPrice));
		} catch (NumberFormatException ex){
			throw new ValidationException(PRICE_NOT_CORRECT);
		}
		return price;
	}

}
