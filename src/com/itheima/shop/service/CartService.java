package com.itheima.shop.service;

import java.util.Map;

import com.itheima.shop.entity.Cart;
import com.itheima.shop.entity.CartItem;
import com.itheima.shop.entity.Product;

/**
 * 处理购物车相关的业务逻辑类
 * @author AllenLei
 * @date 2018年3月20日 下午5:25:40
 */
public class CartService {
	
	// 实例化商品的业务逻辑类
	private ProcuctService procuctService = new ProcuctService();
	
	/**
	 * 购物车添加商品的业务
	 * @param cart
	 * @param pid
	 * @param number
	 */
	public void addProduct(Cart cart, int pid, int number) throws Exception {
		// 1. 判断购物车中是否有商品
		Map<Integer, CartItem> map = cart.getMap();
		// 根据pid 从数据库中获取商品product
		boolean flag = false;
		if (map.size() > 0) {
			// 1.1如果有商品则往商品中加数据就行了
			if (map.containsKey(pid)) {
				CartItem cartItem = map.get(pid);
				cartItem.setNumber(cartItem.getNumber() + number);
				flag = true;
			} 
		}  
		// 1.1 如果没有商品则要往商品中添加商品和对应的数据
		// 2. 把数据添加好后添加到购物车cart
		if (!flag) {
			CartItem cartItem = new CartItem();
			// 根据商品的pid 获取商品信息,需要调用商品的业务层
			Product product = procuctService.findProductByPid(pid+"");
			cartItem.setProduct(product);
			cartItem.setNumber(number);
			// 封装购物车项放入购物车中
			map.put(pid, cartItem);
		}
	}

	/**
	 * 删除购物车中的商品根据pid
	 * @param cart
	 * @param pid
	 */
	public void deleteProduct(Cart cart, int pid) throws Exception {
		Map<Integer, CartItem> map = cart.getMap();
		map.remove(pid);
	}
	
	/**
	 * 处理业务,修改购物车的数量
	 * @param cart
	 * @param pid
	 * @param num
	 */
	public void updateNum(Cart cart, int pid, int num) throws Exception {
		Map<Integer, CartItem> map = cart.getMap();
		CartItem cartItem = map.get(pid);
		cartItem.setNumber(num);
	} 
	
	
}
