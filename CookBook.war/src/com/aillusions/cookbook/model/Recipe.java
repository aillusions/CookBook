package com.aillusions.cookbook.model;

public class Recipe {
	
	private long recipeId;
	
	private String recipeName;
	private String description;
	private String ingridients;
	private String algorithm;
	private String imgPath;
	private boolean wasCooked;
	
	public Recipe() {
		super();
	}

	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIngridients() {
		return ingridients;
	}
	public String getIngridientsHTML() {
		return ingridients.replaceAll("\n", "<br/>");
	}
	public void setIngridients(String ingridients) {
		this.ingridients = ingridients;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public String getAlgorithmHTML() {
		return algorithm.replaceAll("\n", "<br/>");
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public boolean isWasCooked() {
		return wasCooked;
	}

	public void setWasCooked(boolean wasCooked) {
		this.wasCooked = wasCooked;
	}
	

}
