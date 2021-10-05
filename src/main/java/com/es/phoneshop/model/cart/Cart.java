package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CartItem> items;
	private int totalQuantity;
	private BigDecimal totalCost;

	public Cart() {
		items = new ArrayList<>();
	}

	public Cart(Cart cart)  {
		items = cart.getItems();
		totalCost = cart.getTotalCost();
		totalQuantity = cart.getTotalQuantity();
	}
	
	public List<CartItem> getItems(){
		return this.items;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	@Override
	public Cart clone() throws CloneNotSupportedException {
		Cart cart = new Cart();
		items.forEach(cartItem -> {
			try {
				cart.getItems().add((CartItem)cartItem.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		});
		cart.setTotalQuantity(totalQuantity);
		cart.setTotalCost(totalCost);
		return cart;
	}
}

