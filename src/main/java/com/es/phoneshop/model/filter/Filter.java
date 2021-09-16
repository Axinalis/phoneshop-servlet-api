package com.es.phoneshop.model.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class Filter{

	private SortField sortField;
	private SortOrder sortOrder;

	private List<String> queryWords;

	public Filter() {
		this(null, null, null);
	}

	public Filter(SortField sortField, SortOrder sortOrder, String query) {
		if(query == null) {
			query = "";
		}
		this.queryWords = new ArrayList<String>(
				Arrays.asList(
						query.split("\\s+")));
		this.setSortField(sortField);
		this.setSortOrder(sortOrder);
	}
	
	public Filter(String query) {
		this(null, null, query);
	}
	
	public SortField getSortField() {
		return sortField;
	}

	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public List<String> getQueryWords() {
		return queryWords;
	}

	public void setQueryWords(List<String> queryWords) {
		this.queryWords = queryWords;
	}
	
	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Filter p = (Filter) o;
		if (p.getSortField() == this.getSortField() 
				&& p.getSortOrder() == this.getSortOrder()
				&& p.getQueryWords().equals(this.getQueryWords())) {
			return true;
		}
		return false;
	}

	public int hashCode() {

		return Objects.hash(this.queryWords, 
				this.sortField, 
				this.sortOrder);

	}

	public String toString() {
		
		return this.getClass().toString() + "{ "
		+ "sortField=" + this.sortField
		+ " | sortOrder=" + this.sortOrder
		+ " | queryWords=" + this.queryWords.toString()
		+ " }";
	}
	
}
