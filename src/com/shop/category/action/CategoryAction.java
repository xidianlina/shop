package com.shop.category.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.category.vo.Category;

/**
 * 一级分类的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class CategoryAction extends ActionSupport implements
		ModelDriven<Category> {

	@Override
	public Category getModel() {
		return null;
	}

}