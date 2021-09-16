package com.es.phoneshop.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

import com.es.phoneshop.comparator.filter.Filter;
import com.es.phoneshop.comparator.filter.FilterMatcher;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class SortingComparator {

	public static int sortProducts(Product p1, Product p2, Filter filter) {
		int result = 0;
		
		if(p1 == null || p2 == null) {
			return nullComparing(p1, p2);
		}
		if (filter == null) {
			return 0;
		}
		if (filter.getSortField() == null || filter.getSortOrder() == null) {
			return (int) (100 * (
					FilterMatcher.percentOfWords(p2, filter) - 
					FilterMatcher.percentOfWords(p1, filter)));
		} else {
			
			switch (filter.getSortField()) {
			
			case DESCRIPTION:
				if(p1.getDescription() == null || p2.getDescription() == null) {
					return nullComparing(p1.getDescription(), p2.getDescription());
				}
				result = p1.getDescription().compareTo(p2.getDescription());
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
				
			case PRICE:
				if(p1.getPrice() == null || p2.getPrice() == null) {
					return nullComparing(p1.getPrice(), p2.getPrice());
				}
				result = p1.getPrice().compareTo(p2.getPrice());
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
				
			case STOCK:
				result = p1.getStock() - p2.getStock();
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
				
			default:
				return 0;

			}
		}
		
	}
	
	private static int nullComparing(Object o1, Object o2) {
		if(o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null) {
			return -1;
		}
		if (o2 == null) {
			return 1;
		}
		return 0;
		
	}
}
