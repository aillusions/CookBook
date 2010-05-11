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

<script>

function wopen (w_url,w_id,w_width,w_height,w_scroll)
{
	window.open(''+w_url+'',''+w_id+'','width='+w_width+', height='+w_height+', scrollbars='+w_scroll+', noresize');
}
</script>



<div align="center">
	<div class="header">	
	</div>
	<div class="main">		
		<div class="subcontainer">			
			<table>
		 		<tr>
		 			<td valign="top" width="620px">
						<span class="head_line"><c:out value="${presentation.currentCategoryName}"/> : <c:out value="${presentation.currentRecipe.recipeName}"/> </span>
		 				<br /><br />
		 			</td>					 			
		 			<td>
						<a href="javascript:window.history.go(-1);" > &lt; назад </a>  &nbsp;|&nbsp;	<a href="index.jsp"> на главную &gt;  </a><br /> <br />
						<c:if test="${!presentation.currentRecipe.wasCooked}">
							<a href="CookBooFormHandler?action=change_to_cooked">не готовила</a>
						</c:if>
						<c:if test="${presentation.currentRecipe.wasCooked}">
							<a href="CookBooFormHandler?action=change_to_notcooked">готовила</a>
						</c:if>		
		 			</td>
		 		</tr>
		 	</table>				
			<table>
		 		<tr>
		 			<td width="230px">
		 			
		 			<img width="200px" height="200px" vspace="0"
		 			style="cursor:pointer;"
		 			onclick="wopen('ImgAccessor?img_name=<c:out value="${presentation.currentRecipe.imgPath}"/>','','350','350','no')" 
		 			src="ImgAccessor?img_name=<c:out value="${presentation.currentRecipe.imgPath}"/>"/>

		 			</td>					 			
		 			<td valign="top">
						<h4> Что нужно: </h4>
						<c:out escapeXml="false" value="${presentation.currentRecipe.ingridientsHTML}"/> <br />				
		 			</td>
		 		</tr>
		 	</table>

			<h4> Что делать: </h4>
			<c:out escapeXml="false" value="${presentation.currentRecipe.algorithmHTML}"/> <br />	
			<br />
			<br />
		</div>
		<div class="tool_box">
			<a href="changeImage.jsp"> Изменить картинку </a> &nbsp;&nbsp;
			<a href="CookBooFormHandler?action=edit_recipe"> Редактировать </a> &nbsp;&nbsp;	
			<a href="changeCategory.jsp"> Переместить в группу </a>	&nbsp;&nbsp;
			<a href="CookBooFormHandler?action=delete_recipe"> Удалить </a>
			
		</div>		
	</div>
	<div class="footer">
	</div>
</div>

</body>
</html>