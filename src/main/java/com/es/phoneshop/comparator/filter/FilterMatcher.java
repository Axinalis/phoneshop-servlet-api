package com.es.phoneshop.comparator.filter;

import com.es.phoneshop.model.product.Product;

import static com.es.phoneshop.constant.ConstantStrings.ALL_WORDS;

public class FilterMatcher {

	public static double matchesInProduct(Product product, Filter filter) {
		int wordsNum = percentOfMatchedWords(product, filter);

		if(filter.getQueryWords()
				.stream()
				.anyMatch(word -> word.equalsIgnoreCase(product.getCode()))){
			return 1;
		}

		return (double) wordsNum / (double) product.getDescription().split("\\s+").length;
	}

	public static double matchesInQuery(Product product, Filter filter){
		int wordsNum = percentOfMatchedWords(product, filter);

		return (double) wordsNum / (double) filter.getQueryWords().size();
	}

	public static int percentOfMatchedWords(Product product, Filter filter) {

		if(filter.getQueryWords() == null || filter.getQueryWords().size() == 0) {
			return 1;
		}

		String description = product.getDescription();
		if(description == null) {
			return 0;
		}

		return (int) filter.getQueryWords().stream()
				.filter(word ->
						description.toLowerCase().contains(word.toLowerCase()))
				.count();
	}
}
