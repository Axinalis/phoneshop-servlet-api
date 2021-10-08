package com.es.phoneshop.comparator.filter;

import com.es.phoneshop.model.product.Product;

import static com.es.phoneshop.constant.ConstantStrings.ALL_WORDS;

public class FilterMatcher {

	public static double percentOfWords(Product product, Filter filter) {
		
		if(filter.getQueryWords() == null 
				|| filter.getQueryWords().size() == 0
				|| filter.getQueryWords().stream()
				.anyMatch(word -> word.equalsIgnoreCase(product.getCode()))) {
			return 1;
		}

		String description = product.getDescription();
		if(description == null) {
			return 0;
		}

		int wordsNum = (int) filter.getQueryWords().stream()
				.filter(word -> 
			description.toLowerCase().contains(word.toLowerCase()))
				.count();

		return (double) wordsNum / (double) description.split("\\s+").length;
	}
}
