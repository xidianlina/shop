package com.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import com.shop.cart.vo.Cart;
import com.shop.cart.vo.CartItem;
import com.shop.product.service.ProductService;
import com.shop.product.vo.Product;

/**
 * 购物车的Action
 * 
 * @author dell
 * 
 */
public class CartAction {
	// 接收商品的pid
	private Integer pid;
	// 接收商品的数量count
	private Integer count;
	// 注入商品的ProductService
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 将购物项添加到购物车的执行方法
	 * 
	 * @return
	 */
	public String addCart() {
		// 封装CartItem对象
		CartItem cartItem = new CartItem();
		// 设置购物项中商品的数量
		cartItem.setCount(count);
		// 根据商品的pid查询商品
		Product product = productService.findByPid(pid);
		// 设置购物项中的商品
		cartItem.setProduct(product);
		// 将购物项添加到购物车
		Cart cart = getCart();
		cart.addCartItem(cartItem);
		return "addCart";
	}

	/**
	 * 清空购物车的方法
	 * 
	 * @return
	 */
	public String clearCart() {
		// 获得购物车对象
		Cart cart = getCart();
		// 调用清空购物车的方法
		cart.clearCart();
		return "clearCart";
	}

	/**
	 * 移除购物项的方法
	 * 
	 * @return
	 */
	public String removeCart() {
		// 获得购物车对象
		Cart cart = getCart();
		// 调用移除购物项的方法
		cart.removeCartItem(pid);
		return "removeCart";
	}
	
	/**
	 * 跳转到购物车的方法
	 * @return
	 */
	public String myCart(){
		return "myCart";
	}

	/**
	 * 从session中获得购物车的方法
	 * 
	 * @return
	 */
	private Cart getCart() {
		Cart cart = (Cart) ServletActionContext.getRequest().getSession()
				.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			ServletActionContext.getRequest().getSession()
					.setAttribute("cart", cart);
		}
		return cart;
	}
}