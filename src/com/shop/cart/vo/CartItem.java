package com.shop.cart.vo;

import com.shop.product.vo.Product;

/**
 * 购物项对象
 * 
 * @author dell
 * 
 */
public class CartItem {
	private Product product;// 购物项中商品的信息
	private int count;// 某一个购物项中购买商品的数量
	private double subtotal;// 某一购物项中购买商品的价格

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * 自动计算小计
	 * @return
	 */
	public double getSubtotal() {
		return count * product.getShop_price();
	}
}