package com.es.phoneshop.validator;

import static com.es.phoneshop.constant.ConstantStrings.*;
import static com.es.phoneshop.enums.CartAddingState.*;
import static org.junit.Assert.*;

import com.es.phoneshop.enums.CartAddingState;
import com.es.phoneshop.enums.PaymentMethodType;
import com.es.phoneshop.exception.PersonalInfoParsingException;
import com.es.phoneshop.exception.ValidationException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Locale;

public class ValidatorTest {

    @Test
    public void testValidatingId(){

        try{
            Validator.validatingId("");
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_IS_NULL, ex.getMessage());
        }

        try{
            Validator.validatingId(null);
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_IS_NULL, ex.getMessage());
        }

        try{
            Validator.validatingId("yyy");
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_NOT_LONG, ex.getMessage());
        }

        try{
            Validator.validatingId("\n");
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_NOT_LONG, ex.getMessage());
        }

        try{
            Validator.validatingId("/10");
            fail();
        } catch (IllegalArgumentException ex){
            assertEquals(ID_NOT_LONG, ex.getMessage());
        }

        long value;

        value = Validator.validatingId("10");
        assertEquals(value, (long) 10);

    }

    @Test
    public void testParsingQuantity(){

        try{
            Validator.parsingQuantity("", Locale.getDefault());
            fail();
        } catch (ValidationException ex){
            assertEquals(IS_EMPTY.toString().toLowerCase(), ex.getMessage());
        }

        try{
            Validator.parsingQuantity(null, Locale.getDefault());
            fail();
        } catch (ValidationException ex){
            assertEquals(IS_EMPTY.toString().toLowerCase(), ex.getMessage());
        }

        try{
            Validator.parsingQuantity("hhh", Locale.getDefault());
            fail();
        } catch (ValidationException ex){
            assertEquals(CartAddingState.NOT_A_NUMBER.toString().toLowerCase(), ex.getMessage());
        }

        try{
            Validator.parsingQuantity("-10", Locale.getDefault());
            fail();
        } catch (ValidationException ex){
            assertEquals(CartAddingState.NEGATIVE_VALUE.toString().toLowerCase(), ex.getMessage());
        }

        int value = Validator.parsingQuantity("10", Locale.getDefault());
        assertEquals(10, value);
    }

    @Test
    public void testParsingPhoneNumber(){

        try{
            Validator.parsingPhoneNumber(null);
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PHONE_NUMBER_IS_NULL, ex.getMessage());
        }

        try{
            Validator.parsingPhoneNumber("");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PHONE_NUMBER_IS_NULL, ex.getMessage());
        }

        try{
            Validator.parsingPhoneNumber("yyyyy");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PHONE_NUMBER_INCORRECT, ex.getMessage());
        }

        try{
            Validator.parsingPhoneNumber("60");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PHONE_NUMBER_INCORRECT, ex.getMessage());
        }

        try{
            Validator.parsingPhoneNumber("6060606060606060606060");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PHONE_NUMBER_INCORRECT, ex.getMessage());
        }

        String phone = Validator.parsingPhoneNumber("+-333 - 7 7 73 - 3 3 - -7 7 -7-");
        assertEquals("+333777333777", phone);

    }

    @Test
    public void testParsingDate(){

        try{
            Validator.parsingDate(null);
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(DATE_EMPTY, ex.getMessage());
        }

        try{
            Validator.parsingDate("");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(DATE_EMPTY, ex.getMessage());
        }

        try{
            Validator.parsingDate("date");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(DATE_INCORRECT, ex.getMessage());
        }

        try{
            Validator.parsingDate("20-10-2021");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(DATE_IS_BEFORE, ex.getMessage());
        }

        try{
            Validator.parsingDate("2021-09-20");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(DATE_IS_BEFORE, ex.getMessage());
        }

        LocalDate date = Validator.parsingDate("2021-10-30");

        assertTrue(date.isAfter(LocalDate.now()));
    }

    @Test
    public void testParsingPaymentType(){
        PaymentMethodType type;

        try{
            type = Validator.parsingPaymentType(null);
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PAYMENT_TYPE_INCORRECT, ex.getMessage());
        }

        try{
            type = Validator.parsingPaymentType("");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PAYMENT_TYPE_INCORRECT, ex.getMessage());
        }

        try{
            type = Validator.parsingPaymentType("1");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PAYMENT_TYPE_INCORRECT, ex.getMessage());
        }

        try{
            type = Validator.parsingPaymentType("BY_CACHE");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PAYMENT_TYPE_INCORRECT, ex.getMessage());
        }

        try{
            type = Validator.parsingPaymentType("lalala");
            fail();
        } catch (PersonalInfoParsingException ex){
            assertEquals(PAYMENT_TYPE_INCORRECT, ex.getMessage());
        }

        type = Validator.parsingPaymentType("By cache");
        assertSame(type, PaymentMethodType.CACHE);
    }

    @Test
    public void testParsingPrice(){
        BigDecimal value;

        assertNull(Validator.parsingPrice(null));
        assertNull(Validator.parsingPrice(""));

        try{
            value = Validator.parsingPrice("no number");
            fail();
        } catch (ValidationException ex){
            assertEquals(PRICE_NOT_CORRECT, ex.getMessage());
        }

        try{
            value = Validator.parsingPrice("four");
            fail();
        } catch (ValidationException ex){
            assertEquals(PRICE_NOT_CORRECT, ex.getMessage());
        }

        try{
            value = Validator.parsingPrice("777,8");
            fail();
        } catch (ValidationException ex){
            assertEquals(PRICE_NOT_CORRECT, ex.getMessage());
        }

        value = Validator.parsingPrice("089");
        assertEquals(0, value.compareTo(new BigDecimal(89)));

        value = Validator.parsingPrice("777");
        assertEquals(0, value.compareTo(new BigDecimal(777)));
    }

}
