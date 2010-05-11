package com.aillusions.cookbook.web;

import com.aillusions.cookbook.model.Category;

public class CategoryDisplayItem {

	private Category category;	
	private boolean categorySelected;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public boolean isCategorySelected() {
		return categorySelected;
	}
	public void setCategorySelected(boolean categorySelected) {
		this.categorySelected = categorySelected;
	}
	public CategoryDisplayItem(Category category, boolean categorySelected) {
		super();
		this.category = category;
		this.categorySelected = categorySelected;
	}
	public CategoryDisplayItem() {
		super();
	}
	
}
