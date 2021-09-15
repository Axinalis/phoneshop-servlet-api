package com.es.phoneshop.model.filter;

import com.es.phoneshop.model.product.Product;

public class FilterMatcher {

	public static double percentOfWords(Product product, Filter filter) {
		if (filter.getQueryWords().stream()
				.anyMatch(word -> word.equals(product.getCode()))) {
			return 1;
		}
		
		String descript = product.getDescription();
		if(descript == null) {
			return 0;
		}

		int wordsNum = (int) filter.getQueryWords().stream()
				.filter(word -> {
			return descript.contains(word);})
				.count();

		return (double) wordsNum / descript.split("//s+").length;
	}
	 
	
}
