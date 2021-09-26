package com.es.phoneshop.validator;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.enums.CartAddingState;

public class ErrorResolver {

    public static String getMessageFromState(CartAddingState state){
        switch(state){
            case IS_EMPTY: return ConstantStrings.FIELD_IS_EMPTY;
            case NOT_A_NUMBER: return ConstantStrings.NOT_A_NUMBER;
            case OUT_OF_STOCK: return ConstantStrings.OUT_OF_STOCK;
            case PRODUCT_ADDED: return ConstantStrings.PRODUCT_ADDED;
            case NEGATIVE_VALUE: return ConstantStrings.NEGATIVE_VALUE;
            default: return "";
        }
    }

    public static String getMessageFromState(String state){
        switch(state){
            case "is_empty": return ConstantStrings.FIELD_IS_EMPTY;
            case "not_a_number": return ConstantStrings.NOT_A_NUMBER;
            case "out_of_stock": return ConstantStrings.OUT_OF_STOCK;
            case "product_added": return ConstantStrings.PRODUCT_ADDED;
            case "negative_value": return ConstantStrings.NEGATIVE_VALUE;
            default: return "";
        }
    }

}
