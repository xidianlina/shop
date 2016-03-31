package com.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shop.user.vo.User;

/**
 * 用户模块持久层
 * 
 * @author dell
 * 
 */
public class UserDao extends HibernateDaoSupport {
	/**
	 * 按用户名查询是否存在该用户:
	 */
	public User findByUsername(String username) {
		String hql = "FROM User WHERE username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 注册用户的信息存入数据库
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		this.getHibernateTemplate().save(user);
	}

	/**
	 * 根据激活码查询用户
	 * 
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		String hql = "FROM User WHERE code = ?";
		List<User> list = this.getHibernateTemplate().find(hql, code);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 持久层修改用户状态的方法
	 * 
	 * @param existUser
	 */
	public void updateUserState(User existUser) {
		this.getHibernateTemplate().update(existUser);
	}

	/**
	 * 持久层根据页面传来的用户名、密码、状态查询用户是否存在
	 * 
	 * @param user
	 * @return
	 */
	public User findLoginUser(User user) {
		String hql = "FROM User WHERE username = ? AND password = ? AND state = ?";
		List<User> list = this.getHibernateTemplate().find(hql,
				user.getUsername(), user.getPassword(), 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}