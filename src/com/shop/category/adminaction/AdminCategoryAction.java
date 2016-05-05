package com.shop.category.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.category.service.CategoryService;
import com.shop.category.vo.Category;

/**
 * 后台管理一级分类的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class AdminCategoryAction extends ActionSupport implements
		ModelDriven<Category> {
	// 模型驱动使用的对象
	private Category category = new Category();

	@Override
	public Category getModel() {
		return category;
	}

	// 注入一级分类的Service
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * 后台查询所有一级分类的执行方法
	 * 
	 * @return
	 */
	public String findAll() {
		// 查询所有的一级分类
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "findAll";
	}

	/**
	 * 添加一级分类的保存方法
	 * 
	 * @return
	 */
	public String save() {
		categoryService.save(category);
		return "saveSuccess";
	}

	/**
	 * 后台删除一级分类的执行方法
	 * 
	 * @return
	 */
	public String delete() {
		// 使用模型驱动接收到一级分类的cid，删除一级分类，同时删除二级分类，必须先根据cid查询再进行删除
		category = categoryService.findByCid(category.getCid());
		// 删除一级分类
		categoryService.delete(category);
		return "deleteSuccess";
	}

	/**
	 * 后台编辑一级分类的方法
	 * 
	 * @return
	 */
	public String edit() {
		// 查询一级分类
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}

	/**
	 * 后台修改一级分类的方法
	 * 
	 * @return
	 */
	public String update() {
		categoryService.update(category);
		return "updateSuccess";
	}
}