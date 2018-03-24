package com.itheima.shop.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.Product;
import com.itheima.shop.util.JdbcUtil;

public class ProductDao {
	private QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
	/**
	 * 获取热门商品
	 * @return findHotProduct
	 */
	public List<Product> findHotProduct() throws SQLException {
		// 定义SQL语句
		String sql = "select * from tab_product where is_hot=1 and pflag=1 limit 0,12";
		 
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	
	/**
	 * 获取最新商品
	 * @return
	 * @throws SQLException
	 */
	public List<Product> findNewProduct() throws SQLException {
		// 定义SQL语句
		String sql = "select * from tab_product where pflag=1 order by pdate limit 0,12";
		 
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	
	/**
	 * 获取商品类别数据
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findAllCategory() throws SQLException {
		String sql = "select * from tab_category order by cid";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}


	/**
	 * 根据类别cid 获取商品的总数
	 * @param cid
	 * @return int count
	 * @throws SQLException
	 */
	public int getProductCount(int cid) throws SQLException {
		String sql = "select count(*) from tab_product where cid=1";
		Long count = (Long) runner.query(sql, new ScalarHandler());
		return count.intValue();
	}
	
	/**
	 * 根据商品的类别分页查询
	 * @param cid 
	 * @param pageSize 
	 * @param curPage 
	 * @return List<Product>
	 */
	public List<Product> findProductByCid(int cid, int curPage, int pageSize) throws SQLException {
		String sql = "select * from tab_product where pflag=1 and cid = ? order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), cid, (curPage- 1)*pageSize, pageSize);
		
	}

	/**
	 * 根据商品的pid获取商品详情的方法
	 * @param pid
	 * @param cid
	 * @return Map<String, Object>
	 */
	public Map<String, Object> findProductByPid(int pid) throws SQLException {
		String sql = "select *from tab_category c, tab_product p where p.cid=c.cid and p.pid=? and  p.pflag=1";
		return runner.query(sql, new MapHandler(), pid);
	}

}
