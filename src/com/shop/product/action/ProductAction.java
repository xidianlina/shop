package com.shop.product.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.category.service.CategoryService;
import com.shop.product.service.ProductService;
import com.shop.product.vo.Product;
import com.shop.utils.PageBean;

/**
 * 商品的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class ProductAction extends ActionSupport implements
		ModelDriven<Product> {
	// 模型驱动使用的对象
	private Product product = new Product();

	@Override
	public Product getModel() {
		return product;
	}

	// 接收一级分类的csid
	private Integer csid;

	public Integer getCsid() {
		return csid;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	// 接收一级分类的cid
	private Integer cid;

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getCid() {
		return cid;
	}

	// 接收当前的页数page
	private int page;

	public void setPage(int page) {
		this.page = page;
	}

	// 注入一级分类的CategoryService
	private CategoryService categoryService;

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 注入ProductService
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * 根据商品的ID查询商品
	 * 
	 * @return
	 */
	public String findByPid() {
		// 根据商品的id查询商品，无需将查询到的商品放入值栈，直接将查询到的商品赋值给模型驱动的对象product即可，这是查询到的商品就在栈顶
		product = productService.findByPid(product.getPid());
		return "findProductByPid";
	}

	/**
	 * 根据一级分类的cid查询商品
	 * 
	 * @return
	 */
	public String findByCid() {
		// 查询所有一级分类集合!由于商品的一级分类已经存在session中了，此处无需再查询
		// List<Category> cList = categoryService.findAll();
		// 根据一级分类的cid带分页的查询商品
		PageBean<Product> pageBean = productService.findByPageCid(cid, page);
		// 将查询到的商品(pageBean)存入到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}

	/**
	 * 根据二级分类的csid查询商品的执行方法
	 * 
	 * @return
	 */
	public String findByCsid() {
		// 根据二级分类的id查询商品
		PageBean<Product> pageBean = productService.findByPageCsid(csid, page);
		// 将查询到的商品(pageBean)存入到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}

}
