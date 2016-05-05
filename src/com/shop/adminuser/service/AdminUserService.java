package com.shop.adminuser.service;

import org.springframework.transaction.annotation.Transactional;

import com.shop.adminuser.dao.AdminUserDao;
import com.shop.adminuser.vo.AdminUser;

/**
 * 后台管理模块的业务层
 * 
 * @author dell
 * 
 */
@Transactional
public class AdminUserService {
	// 注入持久层对象
	private AdminUserDao adminUserDao;

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	/**
	 * 业务层查询用户的方法
	 * 
	 * @param adminUser
	 * @return
	 */
	public AdminUser login(AdminUser adminUser) {
		return adminUserDao.login(adminUser);
	}

}