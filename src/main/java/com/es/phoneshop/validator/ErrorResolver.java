package com.es.phoneshop.validator;

import static com.es.phoneshop.constant.ConstantStrings.*;
import com.es.phoneshop.enums.CartAddingState;

public class ErrorResolver {

    public static String getMessageFromState(CartAddingState state){
        switch(state){
            case IS_EMPTY: return FIELD_IS_EMPTY;
            case NOT_A_NUMBER: return NOT_A_NUMBER;
            case OUT_OF_STOCK: return OUT_OF_STOCK;
            case PRODUCT_ADDED: return PRODUCT_ADDED;
            case NEGATIVE_VALUE: return NEGATIVE_VALUE;
            default: return "";
        }
    }

    public static String getMessageFromState(String state){
        switch(state){
            case "is_empty": return FIELD_IS_EMPTY;
            case "not_a_number": return NOT_A_NUMBER;
            case "out_of_stock": return OUT_OF_STOCK;
            case "product_added": return PRODUCT_ADDED;
            case "negative_value": return NEGATIVE_VALUE;
            default: return "";
        }
    }

}
