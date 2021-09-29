package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CartItem> items;
	private int totalQuantity;
	private BigDecimal totalCost;

	public Cart() {
		items = new ArrayList<>();
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
}

