package com.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import com.shop.user.dao.UserDao;
import com.shop.user.vo.User;
import com.shop.utils.MailUitls;
import com.shop.utils.UUIDUtils;

/**
 * 用户模块业务逻辑层
 * 
 * @author dell
 * 
 */

@Transactional
public class UserService {
	// 注入UserDao
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 按用户名查询用户的逻辑方法:
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * 业务层用户注册的执行方法
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		// 将注册用户的信息存入数据库
		user.setState(0);// 0代表用户未激活，1代表用户已经激活
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
		user.setCode(code);
		// 调用UserDao完成用户注册的操作
		userDao.saveUser(user);
		// 发送激活邮件
		MailUitls.sendMail(user.getEmail(), code);
	}

	/**
	 * 业务层根据激活码查询用户
	 * 
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		return userDao.findByCode(code);
	}

	/**
	 * 业务层修改用户状态的方法
	 * 
	 * @param existUser
	 */
	public void updateUserState(User existUser) {
		userDao.updateUserState(existUser);
	}

	/**
	 * 业务层根据页面传来的用户名、密码、状态查询用户是否存在
	 * @param user
	 * @return
	 */
	public User findLoginUser(User user) {
		return userDao.findLoginUser(user);
	}
}