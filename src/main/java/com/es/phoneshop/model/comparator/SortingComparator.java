package com.es.phoneshop.model.comparator;

import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.filter.Filter;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.filter.FilterMatcher;

public class SortingComparator {

	public static int sortProducts(Product p1, Product p2, Filter filter) {
		
		int result = 0;
		
		if(filter.getSortField() == null || filter.getSortOrder() == null) {
			return (int)(100*(
					FilterMatcher.percentOfWords(p2, filter)
					-FilterMatcher.percentOfWords(p1, filter)));
		} else {
			switch(filter.getSortField()) {
			case DESCRIPTION:
				result = p1.getDescription().compareTo(p2.getDescription());
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
			case PRICE:
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
}
