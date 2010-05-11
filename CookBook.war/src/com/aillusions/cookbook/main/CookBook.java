package com.aillusions.cookbook.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.aillusions.cookbook.model.Category;
import com.aillusions.cookbook.model.Recipe;

public class CookBook {

	private RecipeDao dao;
	private List<Category> categories;
	
	public void deleteCategory(long categoryId){
		categories.remove(getCategory(categoryId));
	}
	public long getCategoryIdByRecipeId(long recipeId){
		long result = -1;
		Iterator<Category> catIt = categories.iterator();
		while(catIt.hasNext()){
			Category currentCat =  catIt.next();
			Iterator<Recipe> recIt = currentCat.getRecipes().iterator();
			while(recIt.hasNext()){
				if(recIt.next().getRecipeId() == recipeId) {
					result = currentCat.getCategoryId();
				}
			}
		}
		return result;
	}
	
	public void deleteRecipe(long catId, long recipeId){
		List<Recipe> recipes = getCategory(catId).getRecipes();
		int index = 0;
		for(Iterator<Recipe> it = recipes.iterator(); it.hasNext();){
			if(it.next().getRecipeId()== recipeId )
				break;
			index ++;
		}
		getCategory(catId).getRecipes().remove(index);
	}
	
	public CookBook(){
		dao = new RecipeDao();
		categories = new ArrayList<Category>();
		load();
	}
	
	public void load(){
		categories = dao.load();
	}
	
	public void save(){
		dao.save(categories);
	}
	
	public List<Long> getAllCategoryIds(){
		List<Long> categoryNames = new ArrayList<Long>();
		for(Iterator<Category> it = categories.iterator(); it.hasNext();){
			Category currCategory = it.next();
			categoryNames.add(currCategory.getCategoryId());
		}
		return categoryNames;
	}
	
	public List<Long> getCategoryRecipeIds(long catId){
		Category category = getCategory(catId);		
		List<Long> recipeNames = new ArrayList<Long>();
		if(category != null){
			for(Iterator<Recipe> it = category.getRecipes().iterator(); it.hasNext();){
				Recipe currRecipe = it.next();
				recipeNames.add(currRecipe.getRecipeId());
			}		
		}
		return recipeNames;
	}	
	
	public long addCategory(String categoryName){
		Category category = new Category();
		category.setCategoryId(System.currentTimeMillis());
		category.setCategoryName(categoryName);
		categories.add(category);
		return category.getCategoryId();
	}
	
	public long addRecipe(String name, String ingridients, String algorithm, long categoryId, String imgFileName ){
		Recipe recipe =  new Recipe();
		recipe.setRecipeName(name);
		recipe.setIngridients(ingridients);
		recipe.setAlgorithm(algorithm);
		recipe.setImgPath(imgFileName);
		recipe.setRecipeId(System.currentTimeMillis());
		getCategory(categoryId).getRecipes().add(recipe);
		return recipe.getRecipeId();
	}
	
	public long getFirstCategoryId(){
		if(categories.size() > 0)
			return categories.get(0).getCategoryId();
		else
			return -1;
	}
	
	public long getFirstRecipeId(long categoryId){
		long result = - 1;
		if(categoryId > 0){
		Category category = getCategory(categoryId);
		if(category != null && category.getRecipes().size() > 0)
			result = category.getRecipes().get(0).getRecipeId();
		}
		return result;
	}
		
	public Category getCategory(long categoryId){
		Category category = null;
		if(categoryId > 0){
			for(Iterator<Category> it = categories.iterator(); it.hasNext();){
				Category currCategory = it.next();
				if(currCategory.getCategoryId()== categoryId){
					category = currCategory;
				}
			}
		}
		return category;
	}
	
	public Recipe getRecipe(long categoryId, long recipeId){
		Recipe recipe = null;
		if(categoryId > 0 && recipeId > 0){
		Category category = getCategory(categoryId);
			if(category != null){
				for(Iterator<Recipe> it = category.getRecipes().iterator(); it.hasNext();){
					Recipe currRecipe = it.next();
					if(currRecipe.getRecipeId() == recipeId){
						recipe = currRecipe;
					}
				}
			}
		}
		return recipe;
	}
}
