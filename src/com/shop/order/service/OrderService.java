package com.shop.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.shop.order.dao.OrderDao;
import com.shop.order.vo.Order;
import com.shop.order.vo.OrderItem;
import com.shop.utils.PageBean;

/**
 * 订单模块的业务层
 * 
 * @author dell
 * 
 */
@Transactional
public class OrderService {
	// 注入OrderDao
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * 业务层保存订单的方法
	 * 
	 * @param order
	 */
	public void save(Order order) {
		orderDao.save(order);
	}

	/**
	 * 业务层查询我的订单的方法
	 * 
	 * @param uid
	 * @param page
	 */
	public PageBean<Order> findByPageUid(Integer uid, Integer page) {
		// 新建PageBean对象
		PageBean<Order> pageBean = new PageBean<>();
		// 设置当前的页数
		pageBean.setPage(page);
		// 设置每页显示的记录数
		Integer limit = 5;
		pageBean.setLimit(limit);
		// 设置总的记录数
		Integer totalCount = null;
		totalCount = orderDao.findByCountUid(uid);
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		Integer totalPage = null;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示的数据集合
		Integer begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPageUid(uid, begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 业务层根据订单的id查询订单的方法
	 * 
	 * @param oid
	 * @return
	 */
	public Order findByOid(Integer oid) {
		return orderDao.findByOid(oid);
	}

	/**
	 * 业务层修改订单的方法
	 * 
	 * @param currentOrder
	 */
	public void updateOrder(Order currentOrder) {
		orderDao.updateOrder(currentOrder);
	}

	/**
	 * 业务层分页查询订单的方法
	 * 
	 * @param page
	 * @return
	 */
	public PageBean<Order> findByPage(Integer page) {
		// 新建PageBean对象
		PageBean<Order> pageBean = new PageBean<>();
		// 设置当前的页数
		pageBean.setPage(page);
		// 设置每页显示的记录数
		Integer limit = 5;
		pageBean.setLimit(limit);
		// 设置总的记录数
		Integer totalCount = null;
		totalCount = orderDao.findByCount();
		pageBean.setTotalCount(totalCount);
		// 设置总的页数
		Integer totalPage = null;
		if (totalCount % limit == 0) {
			totalPage = totalCount / limit;
		} else {
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		// 设置每页显示的数据集合
		Integer begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPage(begin, limit);
		pageBean.setList(list);
		return pageBean;
	}

	/**
	 * 业务层根据订单的id查询订单项的方法
	 * 
	 * @param oid
	 * @return
	 */
	public List<OrderItem> findOrderItem(Integer oid) {
		return orderDao.findOrderItem(oid);
	}

}