package com.itheima.shop.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.itheima.shop.entity.Order;
import com.itheima.shop.entity.OrderItem;
import com.itheima.shop.util.JdbcUtil;

/**
 * 订单业务的数据访问层
 * @author 雷聪
 *
 */
public class OrderDao {
	
	private QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
	// 实例化数据库连接对象//这里需要用到事务，连接对象从线程中拿
	private QueryRunner transRunner = new QueryRunner();
	
	/**
	 * 把订单主表写入数据库中
	 * @param order
	 */
	public int addOrder(Order order) throws SQLException {
		String sql = "insert into tab_order values(?,?,?,?,?,?,?,?)";
		return transRunner.update(JdbcUtil.getConnection(), sql,
									order.getOid(),
									order.getOrdertime(),
									order.getTotal(),
									order.getState(),
									order.getAddress(),
									order.getName(),
									order.getTelephone(),
									order.getUser().getUid());
	}
	
	/**
	 * 向数据库插入订单子表
	 * @param oid 
	 * @param orderItems 
	 * @throws SQLException
	 */
	public int[] addOrderItem(List<OrderItem> orderItems, int oid) throws SQLException {
		String sql = "insert into tab_orderitem values(null,?,?,?,?)";
		// 二维数组存储用来放入的数据。 第一个维数组长度是执行的次数，第二维的长度是？号的个数
		Object[][] params = new Object[orderItems.size()][4];
		// 遍历 orderitems 把参数传入进去
		for (int i = 0; i < orderItems.size(); i++) {
			params[i][0] = orderItems.get(i).getNum();
			params[i][1] = orderItems.get(i).getSubTotal();
			params[i][2] = orderItems.get(i).getProduct().getPid();
			params[i][3] = oid; 
		}
		return transRunner.batch(JdbcUtil.getConnection(), sql, params);
	}

	/**
	 * 
	 * @param oid
	 * @return
	 */
	public int updateOrderState(int oid) throws SQLException {
		String sql = "update tab_order set state=1 where oid=?";
		return runner.update(sql, oid);
		
		
	}
	
	/**
	 *  根据uid 获取全部信息。
	 * @param uid
	 * @return
	 */
	public List<Order> getOrderListByID(int uid) throws SQLException {
		String sql = "select * from tab_order where uid=?";
		return runner.query(sql, new BeanListHandler<Order>(Order.class), uid);
	}
	
	
	/**
	 * 根据oid 获取订单项
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getOrderItemsByOid(int oid) throws SQLException {
		String sql = "select * from tab_orderItem o, tab_product p where o.pid=p.pid and o.oid=?";
		 
		return runner.query(sql, new MapListHandler(), oid);
	}


}
