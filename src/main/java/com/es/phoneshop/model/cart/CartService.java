package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

	Cart getCart(HttpServletRequest request);
	void add(Long productId, int quantity, HttpServletRequest request);
	void remove(Long productId, int quantity, HttpServletRequest request);
	
}
