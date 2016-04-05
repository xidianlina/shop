package com.shop.category.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.shop.categorysecond.vo.CategorySecond;

/**
 * 一级分类的实体类Category
 * 
 * @author dell
 * 
 */
public class Category implements Serializable {
	private Integer cid;// 一级分类的ID
	private String cname;// 一级分类的name
	// 一级分类中存放的二级分类的集合
	private Set<CategorySecond> categorySeconds = new HashSet<CategorySecond>();

	public Set<CategorySecond> getCategorySeconds() {
		return categorySeconds;
	}

	public void setCategorySeconds(Set<CategorySecond> categorySeconds) {
		this.categorySeconds = categorySeconds;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}