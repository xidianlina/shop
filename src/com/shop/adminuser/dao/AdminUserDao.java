package com.shop.adminuser.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shop.adminuser.vo.AdminUser;

/**
 * 后台管理模块的持久层
 * 
 * @author dell
 * 
 */
public class AdminUserDao extends HibernateDaoSupport {

	/**
	 * 持久层查询用户的方法
	 * 
	 * @param adminUser
	 * @return
	 */
	public AdminUser login(AdminUser adminUser) {
		String hql = "from AdminUser where username = ? and password = ?";
		List<AdminUser> list = this.getHibernateTemplate().find(hql, adminUser.getUsername(),adminUser.getPassword());
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}