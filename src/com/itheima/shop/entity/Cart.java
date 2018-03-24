package com.itheima.shop.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车类,购物车里面有购物信息
 * @author AllenLei
 * @date 2018年3月20日 下午4:59:06
 */
public class Cart {
	
	// 有购物项
	private Map<Integer, CartItem> map;
	// 还有商品的总计个数,暂时还是用不到,不过没有关系
	private int allNumber;
	// 商品的总共价格
	private double total;
	
	public Cart() {
		// 这里每次都要判断购物车里面的购物项是否为空,因为是组合关系,所以这里在生成空仓构造时先实例一个空的购物项
		map = new HashMap<Integer, CartItem>();
	}
	
	public Cart(Map<Integer, CartItem> map, int allNumber, double total) {
		super();
		this.map = map;
		this.allNumber = allNumber;
		this.total = total;
	}

	public Map<Integer, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<Integer, CartItem> map) {
		this.map = map;
	}

	public int getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(int allNumber) {
		this.allNumber = allNumber;
	}

	public double getTotal() {
		// 这里让total等于所有的购物项的小计相加
		total = 0;
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			total += cartItem.getSubTotal();
		}
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	} 
	
	
	
	
}
