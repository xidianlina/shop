package com.shop.categorysecond.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.shop.category.vo.Category;
import com.shop.product.vo.Product;

/**
 * 商品的二级分类的实体类
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class CategorySecond implements Serializable {
	private Integer csid;
	private String csname;
	// 所属的一级分类，存的是一级分类的对象
	private Category category;
	// 配置商品的集合
	private Set<Product> products = new HashSet<Product>();

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Integer getCsid() {
		return csid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	public String getCsname() {
		return csname;
	}

	public void setCsname(String csname) {
		this.csname = csname;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}