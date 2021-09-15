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
        	filter = new Filter(
            		(sortField != null) ? SortField.valueOf(sortField.toUpperCase()) : null, 
            		(sortOrder != null) ? SortOrder.valueOf(sortOrder.toUpperCase()) : null, 
            		query);
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
