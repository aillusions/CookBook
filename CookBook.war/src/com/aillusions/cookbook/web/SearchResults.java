package com.aillusions.cookbook.web;

import java.util.List;

public class SearchResults {
	 private List<RecipeDisplayItem> items;
	 private long quantity;
	public List<RecipeDisplayItem> getItems() {
		return items;
	}
	public void setItems(List<RecipeDisplayItem> items) {
		this.items = items;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public SearchResults(List<RecipeDisplayItem> items, long quantity) {
		super();
		this.items = items;
		this.quantity = quantity;
	}
	public SearchResults() {
		super();

	}
	 
	 
 
}
