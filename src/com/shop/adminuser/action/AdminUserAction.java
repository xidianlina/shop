package com.shop.adminuser.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.adminuser.service.AdminUserService;
import com.shop.adminuser.vo.AdminUser;

/**
 * 后台登录的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class AdminUserAction extends ActionSupport implements
		ModelDriven<AdminUser> {
	// 模型驱动使用的对象
	private AdminUser adminUser = new AdminUser();

	@Override
	public AdminUser getModel() {
		return adminUser;
	}

	// 注入业务层对象
	private AdminUserService adminUserService;

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * 后台登录的方法
	 * 
	 * @return
	 */
	public String login() {
		// 调用service方法完成登录
		AdminUser existAdminUser = adminUserService.login(adminUser);
		// 判断
		if (existAdminUser == null) {
			// 用户名或密码错误
			this.addActionError("用户名或密码错误!");
			return "loginFail";
		} else {
			// 登录成功:
			ServletActionContext.getRequest().getSession()
					.setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}

}