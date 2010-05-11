package com.aillusions.cookbook.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aillusions.cookbook.web.CookBookPresentation;
import com.aillusions.cookbook.web.RecipeDisplayItem;
import com.aillusions.cookbook.web.SearchResults;

public class CookBooFormHandler extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
  
	static final long serialVersionUID = 1L;
	
	private static String IMG_DIR = "D:/CookBook/Data/img/";
	
	private static String NEW_CATEGORY_INPUT_PARAM = "newCategoryName";
	private static String ACTION_INPUT_PARAM= "action";
	private static String SELECTED_CAT_INPUT_PARAM = "selected_cat";
	private static String SELECTED_RECIPE_INPUT_PARAM = "selected_recipe";
	
	private static String NEW_RECIPE_NAME_INPUT_PARAM = "newRecipeName";
	private static String NEW_RECIPE_INGRIDIENTS_INPUT_PARAM = "newRecipeIngridients";
	private static String NEW_RECIPE_ALGORITHM_INPUT_PARAM = "newRecipeAlgorithm";
	private static String NEW_RECIPE_FILENAME_PARAM = "imageFileName";
	private static String NEW_RCATEGORY_SELECTED_PARAM = "newCategorySelected";
	private static String WHAT_SEARCH_PARAM = "what";
	private static String WHERE_SEARCH_PARAM = "where";
	
	
	private static String PRESENTATION_SESSION_BEAN = "presentation";
	private static String ACTION_ADD_NEW_CAT = "add_new_category";
	private static String ACTION_ADD_NEW_RECIPE = "add_new_recipe";
	private static String ACTION_SELECT_CAT = "select_cat";	
	private static String ACTION_SELECT_RECIPE = "select_recipe";	
	
	private static String ACTION_DELETE_CAT = "delete_cat";
	private static String ACTION_DELETE_CAT_SURE = "delete_cat_sure";
	private static String ACTION_EDIT_CAT = "edit_cat";
	private static String ACTION_EDIT_CAT_SAVE = "edit_cat_save";
	private static String ACTION_DELETE_RECIPE = "delete_recipe";
	private static String ACTION_DELETE_RECIPE_SURE = "delete_recipe_sure";
	private static String ACTION_EDIT_RECIPE = "edit_recipe";
	private static String ACTION_EDIT_RECIPE_SAVE = "edit_recipe_save";
	private static String ACTION_CHANGE_IMG = "change_img";
	private static String ACTION_CHANGE_IMG_SAVE = "change_img_save";
	private static String ACTION_SEARCH = "search";	
	private static String ACTION_MOVE_TO_CAT = "move_to_category";	
	private static String ACTION_CANCEL = "cancel_operation";
	private static String ACTION_CHANGE_TO_NOTCOOKED = "change_to_notcooked";
	private static String ACTION_CHANGE_TO_COOKED = "change_to_cooked";
	   
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = (String)request.getParameter(ACTION_INPUT_PARAM);
		CookBookPresentation presentation = getPresentation(request);
		if(action!= null){
			if(action.equals(ACTION_ADD_NEW_CAT)){			
				String newCategoryName = (String)request.getParameter(NEW_CATEGORY_INPUT_PARAM);
				presentation.addNewCategory(newCategoryName);			        
			}else
			if(action.equals(ACTION_SELECT_CAT)){						
				long selectedCategoryId = Long.parseLong(request.getParameter(SELECTED_CAT_INPUT_PARAM));	
				presentation.setCurrentCategoryId(selectedCategoryId);			       
			}else
			if(action.equals(ACTION_SELECT_RECIPE)){			
				long selectedRecipeId = Long.parseLong((String)request.getParameter(SELECTED_RECIPE_INPUT_PARAM));
				presentation.setCurrentRecipeId(selectedRecipeId);		
				request.getRequestDispatcher("/recipe.jsp").forward(request, response);
				return;
			}else
			if(action.equals(ACTION_DELETE_CAT)){			
				request.getRequestDispatcher("/deleteCategoryConfirm.jsp").forward(request, response);
				return;		       
			}else
			if(action.equals(ACTION_DELETE_CAT_SURE)){			
				presentation.deleteCategory();			       
			}else
			if(action.equals(ACTION_EDIT_CAT)){	
				request.getRequestDispatcher("/editCategory.jsp").forward(request, response);
				return;
			}else		
			if(action.equals(ACTION_EDIT_CAT_SAVE)){
				String newCategoryName = (String)request.getParameter(NEW_CATEGORY_INPUT_PARAM);
				presentation.renameCategory(newCategoryName);
			}else	
			if(action.equals(ACTION_DELETE_RECIPE)){			
				request.getRequestDispatcher("/deleteRecipeConfirm.jsp").forward(request, response);
				return;	
			}else
			if(action.equals(ACTION_DELETE_RECIPE_SURE)){			
				presentation.deleteRecipe();			       
			}else
			if(action.equals(ACTION_EDIT_RECIPE)){
				request.getRequestDispatcher("/editRecipe.jsp").forward(request, response);
				return;	
			}
			if(action.equals(ACTION_EDIT_RECIPE_SAVE)){				
				String newRecipeName = (String)request.getParameter(NEW_RECIPE_NAME_INPUT_PARAM);
				String newRecipeIngridients = (String)request.getParameter(NEW_RECIPE_INGRIDIENTS_INPUT_PARAM);
				String newRecipeAldoritm =(String)request.getParameter(NEW_RECIPE_ALGORITHM_INPUT_PARAM);				
				presentation.editRecipe(newRecipeName, newRecipeIngridients, newRecipeAldoritm);
			}			
			if(action.equals(ACTION_SEARCH)){
				String what = (String)request.getParameter(WHAT_SEARCH_PARAM);
				String where = (String)request.getParameter(WHERE_SEARCH_PARAM);
				SearchResults serchResult = presentation.doSearch(what, where);
				request.setAttribute("serchResult", serchResult);
				request.setAttribute("what", what);
				request.setAttribute("where", where);
				request.getRequestDispatcher("/searchResults.jsp").forward(request, response);
				return;
			}
			if(action.equals(ACTION_MOVE_TO_CAT)){
				String catSelected = (String)request.getParameter(NEW_RCATEGORY_SELECTED_PARAM);
				presentation.moveRecipeToCategory(Long.parseLong(catSelected));
			}
			if(action.equals(ACTION_CANCEL)){
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			}
			if(action.equals(ACTION_CHANGE_TO_COOKED)){
				presentation.getCurrentRecipe().setWasCooked(true);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				presentation.save();
				return;
			}
			if(action.equals(ACTION_CHANGE_TO_NOTCOOKED)){
				presentation.getCurrentRecipe().setWasCooked(false);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				presentation.save();
				return;
			}
			
		}else{
			Map map = MultipartFormReader.read(request);		
			action = (String)map.get(ACTION_INPUT_PARAM);
			if(action!= null && action.equals(ACTION_ADD_NEW_RECIPE)){
				String newRecipeName = new String(((String)map.get(NEW_RECIPE_NAME_INPUT_PARAM)).getBytes("windows-1251"), "UTF-8").toString();
				String newRecipeIngridients = new String(((String)map.get(NEW_RECIPE_INGRIDIENTS_INPUT_PARAM)).getBytes("windows-1251"), "UTF-8").toString();
				String newRecipeAldoritm =new String(((String)map.get(NEW_RECIPE_ALGORITHM_INPUT_PARAM)).getBytes("windows-1251"), "UTF-8").toString();
				
				String fileName = ((DataSource)map.get(NEW_RECIPE_FILENAME_PARAM)).getName().trim();
				if(fileName.length() > 0){
					byte[] file =((DataSource) map.get(NEW_RECIPE_FILENAME_PARAM)).getBinaryContent();
	
					fileName =  System.currentTimeMillis() + fileName.replaceAll("\\W", ".");
					FileOutputStream ofs = new FileOutputStream(IMG_DIR + fileName);
					ofs.write(file);
					ofs.flush();
					ofs.close();	
				}
				presentation.addNewRecipe(newRecipeName, newRecipeIngridients, newRecipeAldoritm, fileName);				
			}else
			if(action!= null && action.equals(ACTION_CHANGE_IMG_SAVE)){	
				String fileName = ((DataSource)map.get(NEW_RECIPE_FILENAME_PARAM)).getName();
				byte[] file =((DataSource) map.get(NEW_RECIPE_FILENAME_PARAM)).getBinaryContent();

				fileName =  System.currentTimeMillis() + fileName.replaceAll("\\W", ".");
				FileOutputStream ofs = new FileOutputStream(IMG_DIR + fileName);
				ofs.write(file);
				ofs.flush();
				ofs.close();							
				presentation.changeRecipeImage(fileName);				
			}
		}	
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	private CookBookPresentation getPresentation(HttpServletRequest request){
		CookBookPresentation presentation = (CookBookPresentation)request.getSession().getAttribute(PRESENTATION_SESSION_BEAN);
		
		if(presentation == null){
			presentation = new CookBookPresentation();
			request.getSession().setAttribute(PRESENTATION_SESSION_BEAN, presentation);
		}
		return presentation;
	}
	
}