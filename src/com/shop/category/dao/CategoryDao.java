package com.shop.category.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shop.category.vo.Category;

/**
 * 一级分类的持久层
 * 
 * @author dell
 * 
 */
public class CategoryDao extends HibernateDaoSupport {
	/*
	 * 持久层查询所有一级分类的方法
	 * 
	 * @return
	 */
	public List<Category> findAll() {
		String hql = "FROM Category";
		List<Category> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/*
	 * 持久层保存一级分类的方法
	 * 
	 * @param category
	 */
	public void save(Category category) {
		this.getHibernateTemplate().save(category);
	}

	/*
	 * 持久层根据一个分类的cid查询一级分类的方法
	 * 
	 * @param cid
	 * 
	 * @return
	 */
	public Category findByCid(Integer cid) {
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	/*
	 * 持久层删除一级分类的方法
	 * 
	 * @param category
	 */
	public void delete(Category category) {
		this.getHibernateTemplate().delete(category);
	}

	/**
	 * 持久层修改一级分类的方法
	 * 
	 * @param category
	 */
	public void update(Category category) {
		this.getHibernateTemplate().update(category);
	}

}