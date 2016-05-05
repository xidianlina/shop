package com.shop.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.shop.adminuser.vo.AdminUser;

/**
 * 后台权限校验的拦截器 对没有登录的用户进行拦截
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class PrivilegeInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation actionInvocation)
			throws Exception {
		// 判断session中是否保存了登录用户的信息
		AdminUser existAdminUser = (AdminUser) ServletActionContext
				.getRequest().getSession().getAttribute("existAdminUser");
		if (existAdminUser == null) {
			// 没有登录
			ActionSupport actionSupport = (ActionSupport) actionInvocation
					.getAction();
			actionSupport.addActionError("您还没有登录,没有权限进行访问!请先去登录!");
			return "loginFail";
		} else {
			// 已经登录
			return actionInvocation.invoke();
		}
	}

}
