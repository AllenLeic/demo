package com.itheima.shop.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.shop.dao.OrderDao;
import com.itheima.shop.entity.Cart;
import com.itheima.shop.entity.CartItem;
import com.itheima.shop.entity.Order;
import com.itheima.shop.entity.OrderItem;
import com.itheima.shop.entity.Product;
import com.itheima.shop.entity.User;
import com.itheima.shop.util.JdbcUtil;
import com.itheima.shop.util.UuidUtil;

/**
 * 这是用来处理订单业务的业务逻辑类
 * @author 雷聪
 *
 */
public class OrderService {
	
	// 实例数据访问层
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 处理跳转到订单详情的业务
	 * @param cart
	 * @param loginUser 
	 * @return Order
	 */
	public Order toOrderInfoJsp(Cart cart, User loginUser) throws Exception {
		// 1.创建一个订单对象
		// 2.把相关的信息封装进去，有：下单时间，订单编号，订单总价，订单状态，订单用户
		Order order = new Order();
		order.setOid(UuidUtil.getOid());
		order.setState(0);
		order.setTotal(cart.getTotal());
		order.setUser(loginUser);
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		// 遍历cart将购物车中的每个商品都添加到一个订单项里面
		for (CartItem cartItem : cart.getMap().values()) {
			OrderItem orderItem = new OrderItem();
			// 封装商品
			orderItem.setProduct(cartItem.getProduct());
			// 封装数量
			orderItem.setNum(cartItem.getNumber());
			// 封装小计
			orderItem.setSubTotal(cartItem.getSubTotal());
			orderItem.setOrder(order);
			orderItems.add(orderItem);
		}
		order.setOrderItemList(orderItems);
		return order;
	}
	
	/**
	 * 处理向数据库写入订单的业务
	 * @param order
	 * @return 
	 * @throws Exception
	 */
	public boolean confirmOrder(Order order) throws Exception {
		// 封装订单生成的时间
		order.setOrdertime(new Date());
		// 开启事务
		JdbcUtil.startTransaction();
		try {
			// 向数据库写入主表
			orderDao.addOrder(order);
			// 向数据库写入子表
			orderDao.addOrderItem(order.getOrderItemList(), order.getOid());
			// 如果没有任何错误，则提交事务
			JdbcUtil.commitAndRelease();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback();
			return false;
		}
	}

	/**
	 * 更新支付状态
	 * @param oid
	 */
	public void updateOrderState(int oid) throws Exception {
		orderDao.updateOrderState(oid);
		
	}
	
	/**
	 * 获取用户个人订单
	 * @param loginUser
	 * @return
	 */
	public List<Order> getOrderListByID(User loginUser) throws Exception {
		// 获取用户订单信息，
		// 先根据pid获取订单主表，再在主表里面封装订单项
		List<Order> orderList = orderDao.getOrderListByID(loginUser.getUid());
		// 那么遍历这个主订单表，然后在里面添加数据。
		for (Order order : orderList) {
			// 这里的order 是 一个订单。
			int oid = order.getOid();
			// 然后从数据库中获取这个订单的每个订单项
			List<Map<String, Object>> mapList = orderDao.getOrderItemsByOid(oid);
			// 定义一个集合用来存orderItem
			List<OrderItem> orderItems = new ArrayList<>();
			// 判空
			if (mapList != null && mapList.size() > 0) {
				// 把获取的订单项封装
				for (Map<String, Object> map : mapList) {
					OrderItem orderItem = new OrderItem();
					BeanUtils.populate(orderItem, map);
					// 还要封装product
					Product product = new Product();
					BeanUtils.populate(product, map);
					// 有两项是需要单独封装的，subTotal，num
					orderItem.setNum(Integer.parseInt(map.get("count").toString()));
					orderItem.setSubTotal(Double.parseDouble(map.get("count").toString()));
					// 封装产品
					orderItem.setProduct(product);
					orderItems.add(orderItem);
					
				}
				// 把所有的订单项加入进去
				order.setOrderItemList(orderItems);
			}
		}
		return orderList;
	}
	
		
	
}
