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

<jsp:useBean id="serchResult" class="com.aillusions.cookbook.web.SearchResults" scope="request" />
<jsp:useBean id="what" class="java.lang.String" scope="request" />

<div align="center">
	<div class="header">	
	</div>
	<div class="main">		
		<div class="subcontainer">
			<table>
		 		<tr>
		 			<td valign="top" width="680px">
						<span class="head_line">  <c:out value="${serchResult.quantity}"/> рецептов найдено для запроса "<c:out value="${what}"/>" в 
							<c:if test="${where == 'anywere'}">
								<b>"Везде"</b>
							</c:if>
							<c:if test="${where == 'cooked'}">
								<b>"Готовила"</b>
							</c:if>
							<c:if test="${where == 'notcooked'}">
								<b>"Не готовила"</b>
							</c:if>	
						</span>
		 			</td>					 			
		 			<td>
						<a href="index.jsp"> на главную &gt;  </a>	
		 			</td>
		 		</tr>
		 	</table>

			<br /><br />

			<c:forEach var="recipeItem" items="${serchResult.items}" >					  
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
								</a>
				 			</td>
				 		</tr>
				 	</table>					 	
			</c:forEach>
		</div>
		<div class="tool_box">

		</div>		
	</div>
	<div class="footer">
	</div>
</div>

</body>
</html>