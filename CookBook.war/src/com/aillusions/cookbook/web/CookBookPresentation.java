package com.aillusions.cookbook.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.aillusions.cookbook.main.CookBook;
import com.aillusions.cookbook.model.Category;
import com.aillusions.cookbook.model.Recipe;

public class CookBookPresentation {


	public static String ANYWERE_SEARCH_CONDITION = "anywere";
	public static String COOKED_SEARCH_CONDITION = "cooked";
	public static String NOTCOOKED_SEARCH_CONDITION = "notcooked";
	
	private CookBook cookBook;
	
	private long currentCategoryId;
		
	private long currentRecipeId;
	
	public long getAllRecipeQuantity(){
		long result = 0;
		Iterator<Long> catIt = cookBook.getAllCategoryIds().iterator();
		while(catIt.hasNext()){
			Category cat = cookBook.getCategory(catIt.next());
			result += cat.getRecipes().size();
		}		
		return result;
	}
	
	public long getRecipeQuantityInCurrentCategory(){
		return cookBook.getCategory(currentCategoryId).getRecipes().size();
	}
	
	public void moveRecipeToCategory(long catId){
		cookBook.getCategory(catId).getRecipes().add(getCurrentRecipe());
		deleteRecipe();	
	}
	
	public String getCurrentCategoryName(){
		Category cat = cookBook.getCategory(currentCategoryId);
		if(cat!= null)
			return cat.getCategoryName();
		else
			return null;
	}
	
	public void renameCategory(String newName){
		cookBook.getCategory(currentCategoryId).setCategoryName(newName);
		cookBook.save();
	}
	
	public void save(){
		cookBook.save();
	}
	
	private boolean isContainsWords(String what, String where){
		boolean isContains = true;
		String[] whatWords = what.trim().split(" ");
		for(int i = 0; i < whatWords.length; i ++){
			if (!where.contains(whatWords[i])){
				isContains = false;
				break;
			}
		}
		return isContains;
	}
	
	public SearchResults doSearch(String what, String where){
		List<RecipeDisplayItem> searchResult = new ArrayList<RecipeDisplayItem>();
		Iterator<Long> catIt = cookBook.getAllCategoryIds().iterator();
		while(catIt.hasNext()){
			Category cat = cookBook.getCategory(catIt.next());
			Iterator<Recipe> recIt = cat.getRecipes().iterator();
			while(recIt.hasNext()){
				String strIn = "";
				Recipe recipe = recIt.next();
				if(where.equals(ANYWERE_SEARCH_CONDITION)){
					strIn = recipe.getAlgorithm() + recipe.getIngridients() + recipe.getRecipeName();					
					if(isContainsWords(what.trim().toLowerCase(), strIn.toLowerCase()) || what.trim().length() == 0){
						RecipeDisplayItem item = new RecipeDisplayItem(recipe, false);
						searchResult.add(item);
					}
				}else if(where.equals(COOKED_SEARCH_CONDITION)){
					if(recipe.isWasCooked()){
						strIn = recipe.getAlgorithm() + recipe.getIngridients() + recipe.getRecipeName();					
						if(isContainsWords(what.trim().toLowerCase(), strIn.toLowerCase()) || what.trim().length() == 0){
							RecipeDisplayItem item = new RecipeDisplayItem(recipe, false);
							searchResult.add(item);
						}
					}
				}
				else if(where.equals(NOTCOOKED_SEARCH_CONDITION)){
					if(!recipe.isWasCooked()){
						strIn = recipe.getAlgorithm() + recipe.getIngridients() + recipe.getRecipeName();					
						if(isContainsWords(what.trim().toLowerCase(), strIn.toLowerCase()) || what.trim().length() == 0){
							RecipeDisplayItem item = new RecipeDisplayItem(recipe, false);
							searchResult.add(item);
						}
					}
				}
			}
		}
		
		return new SearchResults(searchResult, searchResult.size());
	}
	
	public void editRecipe(String name, String ingridients, String algorithm){
		Recipe recipe = cookBook.getRecipe(currentCategoryId, currentRecipeId);
		recipe.setRecipeName(name);
		recipe.setIngridients(ingridients);
		recipe.setAlgorithm(algorithm);
		cookBook.save();
	}
	public void changeRecipeImage(String newImageName){
		Recipe recipe = cookBook.getRecipe(currentCategoryId, currentRecipeId);
		recipe.setImgPath(newImageName);
		cookBook.save();
	}
	
	public CookBookPresentation() {
		cookBook = new CookBook();
		currentCategoryId = cookBook.getFirstCategoryId();
		currentRecipeId = cookBook.getFirstRecipeId(currentCategoryId);
	}

	public void deleteCategory(){
		cookBook.deleteCategory(currentCategoryId);
		currentCategoryId = cookBook.getFirstCategoryId();
		cookBook.save();
	}
	
	public void deleteRecipe() {
		cookBook.deleteRecipe(currentCategoryId, currentRecipeId);
		currentRecipeId = cookBook.getFirstRecipeId(currentCategoryId);
		cookBook.save();
	}
	
	public void addNewCategory(String catName){
		if(catName != null && catName.trim().length() > 0){
			setCurrentCategoryId(cookBook.addCategory(catName));
		}
		cookBook.save();
	}
	
	public void addNewRecipe(String name, String ingridients, String algorithm, String fileName){
		setCurrentRecipeId(cookBook.addRecipe(name, ingridients, algorithm, currentCategoryId, fileName));
		cookBook.save();
	}
	
	public List<CategoryDisplayItem> getCategoryItems() {
		List<CategoryDisplayItem> result = new ArrayList<CategoryDisplayItem>();
		for(Iterator<Long> it = cookBook.getAllCategoryIds().iterator(); it.hasNext();){
			long catId = it.next();
			result.add(new CategoryDisplayItem(cookBook.getCategory(catId), catId == currentCategoryId));
		}
		return result;
	}

	public List<RecipeDisplayItem> getRecipeItems() {
		List<RecipeDisplayItem> result = new ArrayList<RecipeDisplayItem>();
		for(Iterator<Long> it = cookBook.getCategoryRecipeIds(currentCategoryId).iterator(); it.hasNext();){
			long recipeId = it.next();
			result.add(new RecipeDisplayItem(cookBook.getRecipe(currentCategoryId, recipeId), recipeId == currentRecipeId));
		}
		return result;
	}
	
	public Recipe getCurrentRecipe(){
		Recipe recipe = cookBook.getRecipe(currentCategoryId, currentRecipeId);
		return recipe;
	}
	
	public void setCurrentCategoryId(long currentCategoryName) {
		this.currentCategoryId = currentCategoryName;
		setCurrentRecipeId(cookBook.getFirstRecipeId(currentCategoryId));
	}
	
	public void setCurrentRecipeId(long currentRecipeId) {
		long catId = cookBook.getCategoryIdByRecipeId(currentRecipeId);
		if(catId > 0){
			this.currentCategoryId = catId;
			this.currentRecipeId = currentRecipeId;
		}
	}

	public long getCurrentCategoryId() {
		return currentCategoryId;
	}


}
