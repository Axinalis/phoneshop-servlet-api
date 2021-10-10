package com.es.phoneshop.comparator.filter;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class FilterMatcherTest {

	private Product p;
	private Filter f;
	
	@Before
	public void setup() {
		p = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg");
	}
	
	@Test
	public void test() {
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "Apple Samsung Nokia");
		assertTrue(FilterMatcher.matchesInProduct(p, f) < 0.5);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "A B C D E F G");
		assertTrue(FilterMatcher.matchesInProduct(p, f) > 0.3);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "Samsung Galaxy S");
		assertTrue(FilterMatcher.matchesInProduct(p, f) == 1.0);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "sgs");
		assertTrue(FilterMatcher.matchesInProduct(p, f) == 1.0);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "sam sung sal axy");
		assertTrue(FilterMatcher.matchesInProduct(p, f) > 0.8);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "SAMSUNG GALAXY");
		assertTrue(FilterMatcher.matchesInProduct(p, f) > 0.5);
		
	}

}
