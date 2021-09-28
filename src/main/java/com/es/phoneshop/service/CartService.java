package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
	Cart getCart(HttpServletRequest request);
	void add(Long productId, int quantity, Cart cart);
	void delete(Long productId, Cart cart);
	void update(Long productId, int quantity, Cart cart);
}
