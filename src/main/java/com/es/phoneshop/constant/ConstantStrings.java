package com.es.phoneshop.constant;

import com.es.phoneshop.service.impl.DefaultCartService;

public class ConstantStrings {

	//For DefaultCartService
	public static final String STRING_SESSION_ATTRIBUTE_CART = DefaultCartService.getInstance().getClass().toString() + ".cart";
	public static final String SUCCESS = "success";
	
	//For ProductDetailsPageServlet
	public static final String QUANTITY = "quantity";
    public static final String ERROR = "error";
    public static final String RECENTLY_VIEWED = "recentlyViewed";
    
    //For UserCartServlet
    public static final String CART_LIST = "cartList";
    public static final String PRODUCT_ID = "productId";
    public static final String UPDATING = "updating";
    public static final String CART = "cart";
    
    //For ProductListPageServlet
    public static final String FIELD = "field";
    public static final String ORDER = "order";
    public static final String QUERY = "query";

    public static final String PRODUCT_ADDED = "Product successfully added to cart!";
    public static final String NOT_A_NUMBER = "Please, enter a valid number";
    public static final String FIELD_IS_EMPTY = "Please, enter a number";
    public static final String OUT_OF_STOCK = "No enough products available";
    public static final String NEGATIVE_VALUE = "Enter a positive value";

}
