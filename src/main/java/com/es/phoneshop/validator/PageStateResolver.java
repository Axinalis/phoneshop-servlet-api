package com.es.phoneshop.validator;

import com.es.phoneshop.constant.ConstantStrings;
import com.es.phoneshop.enums.ProductPageState;

public class PageStateResolver {

    public static String getMessageFromState(ProductPageState state){

        switch(state){
            case IS_EMPTY: return ConstantStrings.fieldIsEmpty;
            case NOT_A_NUMBER: return ConstantStrings.notANumber;
            case OUT_OF_STOCK: return ConstantStrings.outOfStock;
            case PRODUCT_ADDED: return ConstantStrings.productAdded;
            case NEGATIVE_VALUE: return ConstantStrings.negativeValue;
            default: return "";
        }
    }

    public static String getMessageFromState(String state){

        switch(state){
            case "is_empty": return ConstantStrings.fieldIsEmpty;
            case "not_a_number": return ConstantStrings.notANumber;
            case "out_of_stock": return ConstantStrings.outOfStock;
            case "product_added": return ConstantStrings.productAdded;
            case "negative_value": return ConstantStrings.negativeValue;
            default: return "";
        }
    }

}
