package com.es.phoneshop.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.es.phoneshop.comparator.filter.Filter;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class SortingComparatorTest {

	private Product p1;
	private Product p2;
	private Product incompleteProduct;
	private Filter f;

	@Before
	public void setup() {
		p1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100,
				"/Samsung/Samsung%20Galaxy%20S.jpg");
		p2 = new Product(2L, "dfd", "Samsung Galaxy S 2", new BigDecimal(150), Currency.getInstance("USD"), 30,
				"/Samsung/Samsung%20Galaxy%20S.jpg");
		incompleteProduct = new Product(3L, "ert", "Samsung Galaxy S 3", new BigDecimal(100),
				Currency.getInstance("USD"), 100, "/Samsung/Samsung%20Galaxy%20S.jpg");
		incompleteProduct.setPrice(null);
		incompleteProduct.setDescription(null);
	}

	@Test
	public void testNullOrNotFullArguments() {
		f = new Filter(SortField.PRICE, SortOrder.ASC, "");
		
		assertEquals(SortingComparator.sortProducts(null, null, null), 0);
		assertEquals(SortingComparator.sortProducts(null, null, new Filter()), 0);
		assertEquals(SortingComparator.sortProducts(p1, p2, null), 0);
		assertEquals(SortingComparator.sortProducts(null, null, f), 0);
		SortingComparator.sortProducts(incompleteProduct, p2, f);

	}

	@Test
	public void testSorting() {
		f = new Filter(SortField.PRICE, SortOrder.ASC, "");
		
		assertTrue(SortingComparator.sortProducts(p1, p2, f) < 0);
		assertTrue(SortingComparator.sortProducts(p2, p1, f) > 0);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "");
		
		assertTrue(SortingComparator.sortProducts(p1, p2, f) < 0);
		assertTrue(SortingComparator.sortProducts(p2, p1, f) > 0);
		
		f = new Filter(SortField.PRICE, SortOrder.ASC, "");
		
		assertTrue(SortingComparator.sortProducts(incompleteProduct, p2, f) < 0);
		assertTrue(SortingComparator.sortProducts(p2, incompleteProduct, f) > 0);
		
		f = new Filter(SortField.DESCRIPTION, SortOrder.ASC, "");
		
		assertTrue(SortingComparator.sortProducts(incompleteProduct, p2, f) < 0);
		assertTrue(SortingComparator.sortProducts(p2, incompleteProduct, f) > 0);
		
	}
}
