package com.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.user.service.UserService;
import com.shop.user.vo.User;

/**
 * 用户模块action类
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	// 模型驱动使用的对象
	private User user = new User();

	// 接收页面传来的验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	// 注入UserService
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		return user;
	}

	/**
	 * 跳转到注册页面的执行方法
	 */
	public String registPage() {
		return "registPage";
	}

	/**
	 * Ajax进行异步校验用户名的执行方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public String findByName() throws IOException {
		// 调用service进行查询
		User existUser = userService.findByUsername(user.getUsername());
		// 获得response对象，向页面输出
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		// 判断用户名是否存
		if (existUser != null) {
			// 若查询到该用户名,用户名已经存在
			response.getWriter().println("<font color='red'>用户名已经存在</font>");
		} else {
			// 若没有查询到该用户,则用户名可以使用
			response.getWriter().println("<font color='green'>用户名可以使用</font>");
		}
		return NONE;
	}

	/**
	 * 用户注册的执行方法
	 * 
	 * @return
	 */
	public String regist() {
		// 判断输入的验证码是否正确
		String checkcodeSession = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("checkcode");
		// 若页面输入的验证码和程序生成的验证码不一致，则验证失败，跳回到注册页面
		if (!checkcode.equalsIgnoreCase(checkcodeSession)) {
			this.addActionError("验证码输入错误!");
			return "checkcodeFail";
		}
		userService.saveUser(user);
		this.addActionMessage("注册成功!请前往邮箱激活!");
		return "msg";
	}

	/**
	 * 用户激活账号的执行方法
	 * 
	 * @return
	 */
	public String active() {
		// 根据激活码查询用户
		User existUser = userService.findByCode(user.getCode());
		// 判断根据激活码查询的用户是否存在
		if (existUser == null) {
			// 若不存在，则激活码是错误的，向页面发送错误信息
			this.addActionMessage("激活失败:激活码错误！");
		} else {
			// 若用户存在，则更改用户的状态
			existUser.setState(1);
			existUser.setCode(null);
			userService.updateUserState(existUser);
			this.addActionMessage("激活成功！请前往登录!");
		}
		return "msg";
	}

	/**
	 * 跳转到登录页面的执行方法
	 * 
	 * @return
	 */
	public String loginPage() {
		return "loginPage";
	}

	/**
	 * 登录的执行方法
	 * 
	 * @return
	 */
	public String login() {
		// 判断输入的验证码是否正确
		String checkcodeSession = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("checkcode");
		// 若页面输入的验证码和程序生成的验证码不一致，则验证失败，跳回到登录页面
		if (!checkcode.equalsIgnoreCase(checkcodeSession)) {
			this.addActionError("验证码输入错误!");
			return "checkcodeError";
		}
		// 根据页面传来的用户名、密码、状态查询用户是否存在
		User loginUser = userService.findLoginUser(user);
		// 判断用户是否存在
		if (loginUser == null) {
			// 若不存在，则向页面发送提示消息
			this.addActionError("登录失败！用户名、密码错误，或者用户尚未注册或激活！");
			// 登录失败，页面跳转到登录页面
			return LOGIN;
		} else {
			// 若用户存在，则将用户的信息存入都session中
			ServletActionContext.getRequest().getSession()
					.setAttribute("loginUser", loginUser);
			// 页面跳转
			return "loginSuccess";
		}
	}

	/**
	 * 用户退出的执行方法，销毁session即可
	 * 
	 * @return
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return "logout";
	}
}