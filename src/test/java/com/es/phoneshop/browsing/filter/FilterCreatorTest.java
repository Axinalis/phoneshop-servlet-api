package com.es.phoneshop.browsing.filter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.es.phoneshop.browsing.filter.Filter;
import com.es.phoneshop.browsing.filter.FilterCreator;
import com.es.phoneshop.constant.SortField;
import com.es.phoneshop.constant.SortOrder;

public class FilterCreatorTest {

	private FilterCreator cr;
	private Filter f;
	
	@Before
	public void setup() {
		cr = new FilterCreator();
	}
	
	@Test
	public void testCreatingFilters() {

		cr.setQuery("Samsung");
		cr.setSorting("price", "asc");
		f = cr.createFilter();
		
		assertEquals(f, new Filter(SortField.PRICE, SortOrder.ASC, "Samsung"));
		
		cr.setQuery(null);
		cr.setSorting("price", "asc");
		f = cr.createFilter();
		
		assertEquals(f, new Filter(SortField.PRICE, SortOrder.ASC, null));
		
		cr.setQuery(null);
		cr.setSorting(null, null);
		f = cr.createFilter();
		
		assertEquals(f, new Filter(SortField.STOCK, SortOrder.DESC, null));
		
	}

}
