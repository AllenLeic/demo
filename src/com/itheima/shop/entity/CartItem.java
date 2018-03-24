package com.itheima.shop.entity;

/**
 * 购物项信息,每条信息包含了一个商品的购物信息
 * @author AllenLei
 * @date 2018年3月20日 下午4:59:56
 */
public class CartItem {
	
	private Product product;
	private int number;
	private double subTotal;
	
	public CartItem() {
		super();
	}

	public CartItem(Product product, int number, double subTotal) {
		super();
		this.product = product;
		this.number = number;
		this.subTotal = subTotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getSubTotal() {
		subTotal = 0;
		double price = product.getShop_price();
		subTotal = price * number;
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
}
