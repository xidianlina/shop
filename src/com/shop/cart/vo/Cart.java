package com.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 * 
 * @author dell
 * 
 */
public class Cart implements Serializable{
	/*
	 * 购物车的属性
	 */
	// 购物项集合:Map的key是商品的pid,value是购物项
	private Map<Integer, CartItem> map = new LinkedHashMap<Integer, CartItem>();

	// Cart对象中有一个cartItems的属性
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	// 购物总计
	private double total;

	public double getTotal() {
		return total;
	}

	/*
	 * 购物车的功能
	 */
	// 1.将购物项添加到购物车
	public void addCartItem(CartItem cartItem) {
		Integer pid = cartItem.getProduct().getPid();
		// 判断购物车中是否已经存在该购物项
		if (map.containsKey(pid)) {
			// 若存在:数量增加,总计=总计+购物项小计
			CartItem existCartItem = map.get(pid);// 获得购物车中原来的购物项
			existCartItem.setCount(existCartItem.getCount() + cartItem.getCount());
		} else {
			// 若不存在:向map中添加购物项,总计=总计+购物项小计
			map.put(pid, cartItem);
		}
		total += cartItem.getSubtotal();
	}

	// 2.从购物车移除购物项
	public void removeCartItem(Integer pid) {
		// 将购物项移除购物车
		CartItem cartItem = map.remove(pid);
		// 总计=总计-移除的购物项小计
		total -= cartItem.getSubtotal();
	}

	// 3.清空购物车
	public void clearCart() {
		// 将所有购物项清空
		map.clear();
		// 总计设置为0
		total = 0;
	}
}