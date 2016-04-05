package com.shop.index.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.shop.category.service.CategoryService;
import com.shop.category.vo.Category;
import com.shop.product.service.ProductService;
import com.shop.product.vo.Product;

/**
 * 登录action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	// 注入一级分类的的CategoryService
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 注入商品的ProductService
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * 访问首页的方法
	 */
	public String execute() {
		// 查询所有一级分类集合
		List<Category> cList = categoryService.findAll();
		// 将一级分类存入到Session的范围:
		ActionContext.getContext().getSession().put("cList", cList);
		// 查询热门商品
		List<Product> hList = productService.findHotProduct();
		// 将查询到的热门商品保存到值栈
		ActionContext.getContext().getValueStack().set("hList", hList);
		// 查询最新商品
		List<Product> newList = productService.findNewProduct();
		// 将查询到的最新商品保存到值栈
		ActionContext.getContext().getValueStack().set("newList", newList);
		return "index";
	}
}