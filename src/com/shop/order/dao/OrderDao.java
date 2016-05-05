package com.shop.order.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shop.order.vo.Order;
import com.shop.order.vo.OrderItem;
import com.shop.utils.PageHibernateCallback;

/**
 * 订单模块的持久层
 * 
 * @author dell
 * 
 */
public class OrderDao extends HibernateDaoSupport {

	/**
	 * 持久层保存订单的方法
	 * 
	 * @param order
	 */
	public void save(Order order) {
		this.getHibernateTemplate().save(order);
	}

	/**
	 * 持久层查询我的订单的数量统计
	 * 
	 * @param uid
	 * @return
	 */
	public Integer findByCountUid(Integer uid) {
		String hql = "select count(*) from Order o where o.user.uid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, uid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return null;
	}

	/**
	 * 持久层分页查询我的订单
	 * 
	 * @param uid
	 * @param begin
	 * @param limit
	 * @return
	 */
	public List<Order> findByPageUid(Integer uid, Integer begin, Integer limit) {
		String hql = "from Order o where o.user.uid = ? order by ordertime desc";
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(hql, new Object[] { uid },
						begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 持久层根据订单的id查询订单的方法
	 * 
	 * @param oid
	 * @return
	 */
	public Order findByOid(Integer oid) {
		return this.getHibernateTemplate().get(Order.class, oid);
	}

	/**
	 * 持久层修改订单的方法
	 * 
	 * @param currentOrder
	 */
	public void updateOrder(Order currentOrder) {
		this.getHibernateTemplate().update(currentOrder);
	}

	/**
	 * 持久层查询所有订单的总记录数
	 * 
	 * @return
	 */
	public Integer findByCount() {
		String hql = "select count(*) from Order";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return null;
	}

	/**
	 * 持久层查询所有订单的方法
	 * 
	 * @param begin
	 * @param limit
	 * @return
	 */
	public List<Order> findByPage(Integer begin, Integer limit) {
		String hql = "from Order order by ordertime desc";
		List<Order> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Order>(hql, null, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 持久层根据订单的id查询订单项的方法
	 * 
	 * @param oid
	 * @return
	 */
	public List<OrderItem> findOrderItem(Integer oid) {
		String hql = "from OrderItem oi where oi.order.oid = ?";
		List<OrderItem> list = this.getHibernateTemplate().find(hql, oid);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

}