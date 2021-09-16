package com.es.phoneshop.model.filter;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;

public class FilterCreator {
	
	private String query;
	private String sortField;
	private String sortOrder;

	public Filter createFilter() {
		
		Filter filter;
		
		if(sortField == null && sortOrder == null && (query == null || "".equals(query) )) {
        	filter = new Filter(SortField.STOCK, SortOrder.DESC, null);
        } else {
        	SortField field = null;
        	SortOrder order = null;
        	
        	try {
        		field = SortField.valueOf(sortField.toUpperCase());
        		order = SortOrder.valueOf(sortOrder.toUpperCase());
        	} catch(IllegalArgumentException ex) {
        		if(field != null) {
        			order = SortOrder.ASC;
        		}
        	}
        	
        	filter = new Filter(field, order, query);
        }
		
		return filter;
		
	}
	
	//The next methods are made for future.
	//If products become more and more complex, 
	//this creator class will make it easier to create them.
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public void setSorting(String sortField, String sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}
	
}
