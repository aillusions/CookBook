<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cook Book</title>
<link href="css/cookbook.css" rel="stylesheet" type="text/css" />

<style type="text/css">  
</style>

</head>
<body>

<jsp:useBean id="presentation" class="com.aillusions.cookbook.web.CookBookPresentation" scope="session" >
</jsp:useBean> 


<div align="center">
	<div class="header">	
	</div>
	<div class="main">		
		<div class="subcontainer_index">
			<table>
		 		<tr>
		 			<td valign="top" width="600px">
						<form  method="post" action="CookBooFormHandler">			
						<input type="hidden" name="action" value="search" />
						<input type="text" name="what" value="" /> 
						<select name="where">
						  <option value="anywere"> Везде </option>	
						  <option value="cooked"> Готовила </option>		
						  <option value="notcooked"> Не готовила </option>			  
						</select>
						<input type="submit" value="Поиск" />
					</form> 
		 			</td>					 			
		 			<td valign="top" style="color:#555757;font-size:12px;font-family:Tahoma,Arial,Helvetica,sans-serif;">
						Всего в книге <c:out value="${presentation.allRecipeQuantity}"/> рецептов.
		 			</td>
		 		</tr>
		 	</table>
			
			<table style="width:750px">
				<tr>
					<td valign="top" style="width:215px">
						<c:forEach var="categoryItem" items="${presentation.categoryItems}" >
							<c:if test="${!categoryItem.categorySelected}">
								<a href="CookBooFormHandler?action=select_cat&selected_cat=<c:out value="${categoryItem.category.categoryId}"/>"> <c:out value="${categoryItem.category.categoryName}"/> </a>
							</c:if>
							<c:if test="${categoryItem.categorySelected}">
								<b><c:out value="${categoryItem.category.categoryName}"/></b>
							</c:if>
							 <br />
						</c:forEach>				
					</td>
					<td valign="top" style="width:430px">			
					 	<b><c:out value="${presentation.currentCategoryName}"/> </b> <span style="color:#555757;font-size:12px;font-family:Tahoma,Arial,Helvetica,sans-serif;">(<c:out value="${presentation.recipeQuantityInCurrentCategory}"/> шт) </span>
						<hr />
						<div class="scroll">
							<c:forEach var="recipeItem" items="${presentation.recipeItems}" >					  
								 	<table>
								 		<tr>
								 			<td>
								 				<a href="CookBooFormHandler?action=select_recipe&selected_recipe=<c:out value="${recipeItem.recipe.recipeId}"/>">
								 					<img width="70px" height="70px" src="ImgAccessor?img_name=<c:out value="${recipeItem.recipe.imgPath}"/>"/>
								 				</a>
								 			</td>					 			
								 			<td valign="top">
								 				<a href="CookBooFormHandler?action=select_recipe&selected_recipe=<c:out value="${recipeItem.recipe.recipeId}"/>">
								 					<c:out value="${recipeItem.recipe.recipeName}"/>												
												<br />
												<c:if test="${recipeItem.recipe.wasCooked}">
													<span style="color:black;font-size:8pt;">готовила</span>
												</c:if>	
												</a>
								 			</td>
								 		</tr>
								 	</table>					 	
							</c:forEach>	
						<div>			
					</td>			
				</tr>		
			</table>	
		</div>
		<div class="tool_box">
			<a href="addNewCategory.jsp">Создать группу</a> <br />
			<a href="CookBooFormHandler?action=edit_cat"> Переименовать группу </a> 
			 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <a href="addNewRecipe.jsp">Новый рецепт</a>	<br />
			<a href="CookBooFormHandler?action=delete_cat"> Удалить группу </a>		
		</div>		
	</div>
	<div class="footer">
	</div>
</div>

</body>
</html>