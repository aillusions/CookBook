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
		<div class="subcontainer">			
			<span class="head_line"> Изменение изображения для рецепта "<c:out value="${presentation.currentRecipe.recipeName}"/>" </span>
			<br /><br />
			<form enctype="multipart/form-data" method="post" action="CookBooFormHandler">
			
				<input type="hidden" name="action" value="change_img_save" />
				Новое изображение:  <br />
				<input type="file" name="imageFileName" value="Обзор" /> <br />
				<input type="submit" value="Сохранить" />  <input type="button" value="Отмена" onclick="javascript:window.history.go(-1);" /> 	
				
			</form> 
		</div>
		<div class="tool_box">

		</div>		
	</div>
	<div class="footer">
	</div>
</div>

</body>
</html>


