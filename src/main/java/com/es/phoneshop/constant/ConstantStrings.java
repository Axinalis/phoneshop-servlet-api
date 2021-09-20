package com.es.phoneshop.constant;

import com.es.phoneshop.model.cart.impl.DefaultCartService;

public class ConstantStrings {

	//For DefaultCartService
	public static final String stringSessionAttributeCart = DefaultCartService.getInstance().getClass().toString() + ".cart";
	public static final String success = "success";
	
	//For ProductDetailsPageServlet
	public static final String quantity = "quantity";
    public static final String error = "error";	
    public static final String recentlyViewed = "recentlyViewed";
    
    //For UserCartServlet
    public static final String stringCartList = "cartList";
    
    //For ProductListPageServlet
    public static final String field = "field";
    public static final String order = "order";
    public static final String query = "query";

    public static final String productAdded = "Product successfully added to cart!";
    public static final String notANumber = "Please, enter a valid number";
    public static final String fieldIsEmpty = "Please, enter a number";
    public static final String outOfStock = "No enough products available";
    public static final String negativeValue = "Enter a positive value";

}
