package com.es.phoneshop.model.viewsHistory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.es.phoneshop.model.Product;

public class UserViewsHistory {

	private Deque<Product> recentlyViewed;
	private static int numberOfLastProducts;
	
	public UserViewsHistory() {
		if(recentlyViewed == null) {
			recentlyViewed = new LinkedList<Product>();
		}
		numberOfLastProducts = 3;
	}

	public Deque<Product> getRecentlyViewed() {
		return recentlyViewed;
	}

	public void setRecentlyViewed(Deque<Product> recentlyViewed) {
		this.recentlyViewed = recentlyViewed;
	}
	
	public void addProduct(Product product) {
		recentlyViewed.remove(product);
		recentlyViewed.addFirst(product);

		if(recentlyViewed.size() > numberOfLastProducts) {
			recentlyViewed.removeLast();
		}

	}
	
}
