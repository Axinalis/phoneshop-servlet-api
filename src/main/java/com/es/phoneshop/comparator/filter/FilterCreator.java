package com.es.phoneshop.comparator.filter;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;

import java.math.BigDecimal;

public class FilterCreator {
	
	private String query;
	private String sortField;
	private String sortOrder;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private String typeOfSearch;

	public Filter createFilter() {
		
		//Default filter with stock sortfield
		if((sortField == null || sortOrder == null) && (query == null || "".equals(query) )) {
        	return new Filter(SortField.STOCK, SortOrder.DESC, null, minPrice, maxPrice, typeOfSearch);
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
    		return new Filter(field, order, query, minPrice, maxPrice, typeOfSearch);
    	}   
		
		//Filter without sorting, only with query
		return new Filter(null, null, query, minPrice, maxPrice, typeOfSearch);
		
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

	public void setPriceSpan(BigDecimal min, BigDecimal max){
		this.maxPrice = max;
		this.minPrice = min;
	}

	public void setTypeOfSearch(String type){
		this.typeOfSearch = type;
	}
	
}
