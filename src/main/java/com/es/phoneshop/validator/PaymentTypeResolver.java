package com.es.phoneshop.validator;

import com.es.phoneshop.enums.PaymentMethodType;

import static com.es.phoneshop.constant.ConstantStrings.*;

public class PaymentTypeResolver {

    public static String GetMessageFromType(PaymentMethodType type){
        switch(type){
            case CACHE: return BY_CACHE;
            case CREDIT_CARD: return VIA_CREDIT_CARD;
            case BITCOINS: return BY_BITCOINS;
            default: return "";
        }
    }

    public static PaymentMethodType GetTypeFromMessage(String message){
        switch(message){
            case BY_CACHE: return PaymentMethodType.CACHE;
            case VIA_CREDIT_CARD: return PaymentMethodType.CREDIT_CARD;
            case BY_BITCOINS: return PaymentMethodType.BITCOINS;
            default: return null;
        }
    }

}
