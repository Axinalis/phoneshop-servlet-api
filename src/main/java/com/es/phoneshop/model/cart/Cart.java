package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	private List<CartItem> items;

	public Cart() {
		items = new ArrayList<CartItem>();
	}
	
	public List<CartItem> getItems(){
		return this.items;
	}
	
}

