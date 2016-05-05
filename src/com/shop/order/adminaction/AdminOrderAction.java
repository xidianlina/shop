package com.shop.order.adminaction;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.order.service.OrderService;
import com.shop.order.vo.Order;
import com.shop.order.vo.OrderItem;
import com.shop.utils.PageBean;

/**
 * 后台管理订单的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class AdminOrderAction extends ActionSupport implements
		ModelDriven<Order> {
	// 模型驱动使用的对象
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	// 接收分页查询的参数page
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 注入订单的Service
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * 后台带分页查询所有订单的方法
	 * 
	 * @return
	 */
	public String findAll() {
		PageBean<Order> pageBean = orderService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findAll";
	}

	/**
	 * 根据订单的id查询订单项
	 * 
	 * @return
	 */
	public String findOrderItem() {
		List<OrderItem> list = orderService.findOrderItem(order.getOid());
		ActionContext.getContext().getValueStack().set("list", list);
		return "findOrderItem";
	}

	/**
	 * 修改订单状态的方法
	 * 
	 * @return
	 */
	public String updateState() {
		// 根据订单的id查询当前的订单
		Order currentOrder = orderService.findByOid(order.getOid());
		// 修改订单的状态为"已发货,等待确认收货"
		currentOrder.setState(3);
		// 调用订单的Service方法修改订单的状态
		orderService.updateOrder(currentOrder);
		return "updateStateSuccess";
	}

}