package com.es.phoneshop.constant;

import com.es.phoneshop.service.impl.DefaultCartService;
import com.es.phoneshop.service.impl.DefaultOrderService;

public class ConstantStrings {

	//For DefaultCartService
	public static final String STRING_SESSION_ATTRIBUTE_CART = DefaultCartService.getInstance().getClass().toString().split(" ")[1] + ".cart";
	public static final String STRING_SESSION_ATTRIBUTE_ORDER = DefaultOrderService.getInstance().getClass().toString().split(" ")[1] + ".cart";
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
    public static final String CURRENT_ORDER = "order";
    public static final String QUERY = "query";

    //Messages for users
    public static final String PRODUCT_ADDED = "Product successfully added to cart!";
    public static final String NOT_A_NUMBER = "Please, enter a valid number";
    public static final String FIELD_IS_EMPTY = "Please, enter a number";
    public static final String OUT_OF_STOCK = "No enough products available";
    public static final String NEGATIVE_VALUE = "Enter a positive value";

    //For MiniCartServlet
    public static final String MINI_CART = "miniCart";
    public static final String MINI_CART_FULL = "miniCartFull";

    //For PersonalDetailsServlet
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String DELIVERY_DATE = "deliveryDate";
    public static final String ADDRESS = "address";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String ERRORS = "errors";
    public static final String VALUES = "values";
    public static final String PAYMENT_TYPES = "paymentTypes";
    //Messages for users
    public static final String PHONE_NUMBER_INCORRECT = "Phone number is incorrect";
    public static final String DATE_INCORRECT = "Date is incorrect";
    public static final String DATE_WRONG = "Date is in the past";
    public static final String DATE_EMPTY = "Date is not choosed";
    public static final String PAYMENT_TYPE_INCORRECT = "Payment type is incorrect";

    //Types of payment (Messages for users)
    public static final String BY_CACHE = "By cache";
    public static final String VIA_CREDIT_CARD = "Via credit card";
    public static final String BY_BITCOINS = "By bitcoins";

    //For FinalCheckoutPageServlet
    public static final String ORDER_PLACED = "orderPlaced";

    //For UserOrdersServlet
    public static final String ORDERS_LIST = "ordersList";

    //For AdvancedSearchPageServlet
    public static final String DESCRIPTION = "description";
    public static final String MIN_PRICE = "minPrice";
    public static final String MAX_PRICE = "maxPrice";
    public static final String ALL_WORDS = "All words";
    public static final String ANY_WORD = "Any word";
    public static final String SEARCH_TYPE = "typeOfSearch";

    public static final String PROJECT_NAME = "/phoneshop-servlet-api";
}
