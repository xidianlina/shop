package com.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.shop.cart.vo.Cart;
import com.shop.cart.vo.CartItem;
import com.shop.order.service.OrderService;
import com.shop.order.vo.Order;
import com.shop.order.vo.OrderItem;
import com.shop.user.vo.User;
import com.shop.utils.PageBean;
import com.shop.utils.PaymentUtil;

/**
 * 订单管理的Action
 * 
 * @author dell
 * 
 */
@SuppressWarnings("serial")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	// 模型驱动的对象
	private Order order = new Order();

	@Override
	public Order getModel() {
		return order;
	}

	// 注入OrderService
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// 接收分页的page参数
	private Integer page;

	public void setPage(Integer page) {
		this.page = page;
	}

	// 接收支付通道编码
	private String pd_FrpId;

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	// 接收付款成功后的参数:
	private String r3_Amt;
	private String r6_Order;

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	/**
	 * 生成订单的方法
	 * 
	 * @return
	 */
	public String saveOrder() {
		// 1.订单的数据保存到数据库
		order.setOrdertime(new Date());
		order.setState(1);// 1代表未付款，2代表已付款，但没有发货，3代表已发货，但没有确认收货，4代表交易完成
		// 总计的数据是购物车中的信息
		Cart cart = (Cart) ServletActionContext.getRequest().getSession()
				.getAttribute("cart");
		if (cart == null) {
			this.addActionError("您还没有购物，请先去购物!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		// 设置订单中的订单项
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			order.getOrderItems().add(orderItem);
		}
		// 设置订单所属的用户
		User loginUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
		if (loginUser == null) {
			this.addActionError("您还没有登录，请先去登录!");
			return "login";
		}
		order.setUser(loginUser);
		// 设置订单的地址
		order.setAddr(loginUser.getAddr());
		// 设置订单的收货人
		order.setName(loginUser.getName());
		// 设置订单的收货人电话
		order.setPhone(loginUser.getPhone());
		orderService.save(order);
		// 2.将订单对象的数据显示到页面:通过值栈的方式显示，因为Order显示的对象就是模型驱动使用的对象
		// 清空购物车
		cart.clearCart();
		return "saveOrder";
	}

	/*
	 * 查询我的订单的执行方法
	 */
	public String findByUid() {
		// 根据用户的id查询
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
		// 调用orderService进行查询
		PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(),
				page);
		// 通过值栈将分页的数据显示到页面
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}

	/**
	 * 根据订单的id查询订单的执行方法
	 * 
	 * @return
	 */
	public String findByOid() {
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}

	/**
	 * 为订单付款的执行方法
	 * 
	 * @return
	 * @throws IOException
	 */
	public String payOrder() throws IOException {
		Order currentOrder = orderService.findByOid(order.getOid());
		// 设置订单的地址
		currentOrder.setAddr(order.getAddr());
		// 设置订单的收货人
		currentOrder.setName(order.getName());
		// 设置订单的收货人电话
		currentOrder.setPhone(order.getPhone());
		// 修改订单
		orderService.updateOrder(currentOrder);
		// 2.完成付款:
		// 付款需要的参数:
		String p0_Cmd = "Buy"; // 业务类型:
		String p1_MerId = "10001126856";// 商户编号:
		String p2_Order = order.getOid().toString();// 订单编号:
		String p3_Amt = "0.01"; // 付款金额:
		String p4_Cur = "CNY"; // 交易币种:
		String p5_Pid = ""; // 商品名称:
		String p6_Pcat = ""; // 商品种类:
		String p7_Pdesc = ""; // 商品描述:
		String p8_Url = "http://localhost:8080/shop/order_callBack.action"; // 商户接收支付成功数据的地址:
		String p9_SAF = ""; // 送货地址:
		String pa_MP = ""; // 商户扩展信息:
		String pd_FrpId = this.pd_FrpId;// 支付通道编码:
		String pr_NeedResponse = "1"; // 应答机制:
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // hmac
		// 向易宝发送请求:
		StringBuffer sb = new StringBuffer(
				"https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		// 重定向:向易宝出发:
		ServletActionContext.getResponse().sendRedirect(sb.toString());
		return NONE;
	}

	/**
	 * 付款成功后跳转回来的路径
	 * 
	 * @return
	 */
	public String callBack() {
		// 修改订单的状态:
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		// 修改订单状态为2:已经付款:
		currOrder.setState(2);
		orderService.updateOrder(currOrder);
		this.addActionMessage("支付成功!订单编号为: " + r6_Order + " 付款金额为: " + r3_Amt);
		return "msg";
	}

	/**
	 * 确认收货:修改订单的状态
	 * 
	 * @return
	 */
	public String updateState() {
		Order currentOrder=orderService.findByOid(order.getOid());
		currentOrder.setState(4);
		orderService.updateOrder(currentOrder);
		return "updateStateSuccess";
	}
}