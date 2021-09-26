package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

	private List<CartItem> items;

	public Cart() {
		items = new ArrayList<CartItem>();
	}
	
	public List<CartItem> getItems(){
		return this.items;
	}
	
}

