package com.shop.product.adminaction;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.categorysecond.service.CategorySecondService;
import com.shop.categorysecond.vo.CategorySecond;
import com.shop.product.service.ProductService;
import com.shop.product.vo.Product;
import com.shop.utils.PageBean;

/**
 * 后台管理商品的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class AdminProductAction extends ActionSupport implements
		ModelDriven<Product> {
	// 模型驱动使用的对象
	private Product product = new Product();

	@Override
	public Product getModel() {
		return product;
	}

	// 文件上传需要的参数
	private File upload;// 上传的文件
	private String uploadFileName;// 接收文件上传的文件名
	private String uploadContextType;// 接收文件上传的文件的MIME的类型

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContextType(String uploadContextType) {
		this.uploadContextType = uploadContextType;
	}

	// 接收分页查询的参数page
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 注入商品的ProductService
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// 注入二级分类的Service
	private CategorySecondService categorySecondService;

	public void setCategorySecondService(
			CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	/**
	 * 后台带分页查询商品的执行方法
	 * 
	 * @return
	 */
	public String findAll() {
		PageBean<Product> pageBean = productService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	/**
	 * 后台管理商品添加商品的执行方法
	 * 
	 * @return
	 */
	public String addPage() {
		// 查询所有二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "addPageSuccess";
	}

	/**
	 * 保存商品的方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public String save() throws IOException {
		product.setPdate(new Date());
		if (upload != null) {
			// 将商品图片上传到服务器上.
			// 获得上传图片的服务器端路径.
			String path = ServletActionContext.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			FileUtils.copyFile(upload, diskFile);
			product.setImage("products/" + uploadFileName);
		}
		// 将商品保存保存到数据库
		productService.save(product);
		return "saveSuccess";
	}

	/**
	 * 后台删除商品的方法
	 * 
	 * @return
	 */
	public String delete() {
		// 先查询再删除
		product = productService.findByPid(product.getPid());
		// 删除上传的文件图片
		String path = product.getImage();
		if (path != null) {
			String realPath = ServletActionContext.getServletContext()
					.getRealPath("/" + path);
			File file = new File(realPath);
			file.delete();
		}
		// 删除数据库中的商品记录
		productService.delete(product);
		return "deleteSuccess";
	}

	/**
	 * 后台编辑商品的方法
	 * 
	 * @return
	 */
	public String edit() {
		// 根据商品的id查询商品
		product = productService.findByPid(product.getPid());
		// 查询所有二级分类的集合
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "editSuccess";
	}

	/**
	 * 后台修改商品的方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public String update() throws IOException {
		product.setPdate(new Date());
		// 文件的上传:
		if (upload != null) {
			// 将原来已有的商品图片删除
			String delPath = ServletActionContext.getServletContext()
					.getRealPath("/" + product.getImage());
			File file = new File(delPath);
			file.delete();
			// 将新的商品图片上传
			// 获得上传图片的服务器端路径.
			String path = ServletActionContext.getServletContext().getRealPath(
					"/products");
			// 创建文件类型对象:
			File diskFile = new File(path + "//" + uploadFileName);
			// 文件上传:
			FileUtils.copyFile(upload, diskFile);

			product.setImage("products/" + uploadFileName);
		}
		// 将信息修改到数据库
		productService.update(product);
		// 页面跳转
		return "updateSuccess";
	}
}