package com.aillusions.cookbook.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: ImgAccessor
 *
 */
 public class ImgAccessor extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   private static String IMG_NAME_INPUT_PARAM = "img_name";
   private static String IMG_DIR = "D:/CookBook/Data/img/";
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		String imageName = (String)request.getParameter(IMG_NAME_INPUT_PARAM);
		response.setContentType("image/jpg");
		FileInputStream fis = null;
		if(imageName.trim().length() > 0 && new File(IMG_DIR + imageName).exists()){
			fis = new FileInputStream(IMG_DIR + imageName);
		}else{
			fis = new FileInputStream(IMG_DIR + "noimage.jpg");
		}
		byte[] readBytes = new byte[100000];
		int readQuantity;
		while( (readQuantity = fis.read(readBytes)) > 0){
			response.getOutputStream().write(readBytes , 0, readQuantity);
		}
		fis.close();
		response.flushBuffer();
		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}