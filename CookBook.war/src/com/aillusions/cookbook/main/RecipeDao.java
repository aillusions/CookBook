package com.aillusions.cookbook.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.aillusions.cookbook.model.Category;
import com.aillusions.cookbook.model.Recipe;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RecipeDao {

	private static String dataFilePath = "d:/CookBook/Data/CookBook.xml";

	XStream xstream = null;

	public RecipeDao() {
		xstream = new XStream(new DomDriver());
		xstream.alias("category", Category.class);
		xstream.alias("recipe", Recipe.class);
	}

	public void save(List<Category> categories) {
		String xml = xstream.toXML(categories);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(dataFilePath);
			fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>".getBytes());
			fos.write("\r\n<?xml-stylesheet type=\"text/xsl\" href=\"Words_prn.xsl\"?>\r\n".getBytes());
			fos.write(xstream.toXML(categories).getBytes("UTF-8"));
			fos.flush();
			fos.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Category> load() {
		FileInputStream is = null;
		try {
			is = new FileInputStream(dataFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List rawList = (List) xstream.fromXML(is);
		return (List<Category>) rawList;
	}
}
