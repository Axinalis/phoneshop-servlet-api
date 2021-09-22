package com.es.phoneshop.browsing.filter;

import com.es.phoneshop.constant.SortField;
import com.es.phoneshop.constant.SortOrder;

public class FilterCreator {
	
	private String query;
	private String sortField;
	private String sortOrder;

	public Filter createFilter() {
		
		//Default filter with stock sortfield
		if((sortField == null || sortOrder == null) && (query == null || "".equals(query) )) {
        	return new Filter(SortField.STOCK, SortOrder.DESC, null);
        } 
		
		//Filter for defined sorting (with or without query)
		if((sortField != null && sortOrder != null)) {
    		
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
    		return new Filter(field, order, query);
    	}   
		
		//Filter without sorting, only with query
		return new Filter(null, null, query);
		
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
