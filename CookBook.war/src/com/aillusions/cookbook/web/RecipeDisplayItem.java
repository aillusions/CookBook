package com.aillusions.cookbook.web;

import com.aillusions.cookbook.model.Recipe;



public class RecipeDisplayItem {

	private Recipe recipe;
	private boolean recipeSelected;
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public boolean isRecipeSelected() {
		return recipeSelected;
	}
	public void setRecipeSelected(boolean recipeSelected) {
		this.recipeSelected = recipeSelected;
	}
	public RecipeDisplayItem(Recipe recipe, boolean recipeSelected) {
		super();
		this.recipe = recipe;
		this.recipeSelected = recipeSelected;
	}
	public RecipeDisplayItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
}
