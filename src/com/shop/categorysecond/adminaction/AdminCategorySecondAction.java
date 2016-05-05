package com.shop.categorysecond.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.category.service.CategoryService;
import com.shop.category.vo.Category;
import com.shop.categorysecond.service.CategorySecondService;
import com.shop.categorysecond.vo.CategorySecond;
import com.shop.utils.PageBean;

/**
 * 后台二级分类管理的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class AdminCategorySecondAction extends ActionSupport implements
		ModelDriven<CategorySecond> {
	// 模型驱动使用的对象
	private CategorySecond categorySecond = new CategorySecond();

	@Override
	public CategorySecond getModel() {
		return categorySecond;
	}

	// 注入二级分类的Service
	private CategorySecondService categorySecondService;

	public void setCategorySecondService(
			CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	// 注入一级分类的Service
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 接收分页查询的信息page
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 后台管理中二级分类的查询方法
	 * 
	 * @return
	 */
	public String findAll() {
		PageBean<CategorySecond> pageBean = categorySecondService
				.findByPage(page);
		// 将查处到的所有二级分类存入值栈
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	/**
	 * 后台添加二级分类的方法
	 * 
	 * @return
	 */
	public String addPage() {
		// 查询所有一级分类
		List<Category> cList = categoryService.findAll();
		// 将查询到的一级分类存入值栈中
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "addPageSuccess";
	}

	/**
	 * 后台保存二级分类的方法
	 * 
	 * @return
	 */
	public String save() {
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}

	/**
	 * 后台删除二级分类的方法
	 * 
	 * @return
	 */
	public String delete() {
		// 如果要级联删除，则要先查询并配置cascade
		categorySecond = categorySecondService.findByCsid(categorySecond
				.getCsid());
		categorySecondService.delete(categorySecond);
		return "deleteSuccess";
	}

	/**
	 * 后台编辑二级分类的方法
	 * 
	 * @return
	 */
	public String edit() {
		categorySecond = categorySecondService.findByCsid(categorySecond
				.getCsid());
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "editSuccess";
	}

	/**
	 * 后台修改二级分类的方法
	 * 
	 * @return
	 */
	public String update() {
		categorySecondService.update(categorySecond);
		return "updateSuccess";
	}

}
