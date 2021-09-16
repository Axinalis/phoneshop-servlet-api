package com.es.phoneshop.comparator.filter;

import java.util.List;
import java.util.stream.Collectors;

import com.es.phoneshop.model.product.Product;

public class FilterMatcher {

	public static double percentOfWords(Product product, Filter filter) {
		
		if(filter.getQueryWords() == null 
				|| filter.getQueryWords().size() == 0
				|| filter.getQueryWords().stream()
				.anyMatch(word -> word.toLowerCase().equals(product.getCode().toLowerCase()))) {
			return 1;
		}

		String descript = product.getDescription();
		if(descript == null) {
			return 0;
		}

		int wordsNum = (int) filter.getQueryWords().stream()
				.filter(word -> {
			return descript.toLowerCase().contains(word.toLowerCase());})
				.count();

		descript.split("\\s+");
		
		return (double) wordsNum / (double) descript.split("\\s+").length;
	}
	 
	
}