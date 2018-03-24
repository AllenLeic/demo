package com.itheima.shop.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.itheima.shop.dao.ProductDao;
import com.itheima.shop.entity.Category;
import com.itheima.shop.entity.PageBean;
import com.itheima.shop.entity.Product;
import com.itheima.shop.util.CommonUtil;
import com.itheima.shop.util.JedisUtil;

public class ProcuctService {
	
	// 实例Dao层
	ProductDao productDao = new ProductDao();
	/**
	 * 获取热门商品的数据
	 * @return
	 */
	public List<Product> findHotProduct() throws Exception {
		List<Product> product = productDao.findHotProduct();
		return product;
	}
	
	/**
	 * 获取最新商品的数据
	 * @return
	 * @throws Exception
	 */
	public List<Product> findNewProduct() throws Exception {
		List<Product> product = productDao.findNewProduct();
		return product;
	}
	
	/**
	 * 利用ajax获取商品分类数据
	 * @return
	 */
	public String findAllCategory() throws Exception {
		// 创建jedis对象
		Jedis jedis = JedisUtil.getJedis();
		// 1.先去Redis缓存数据库中找数据
		// 2.判断数据的有效性
		String jsonDate = jedis.get(CommonUtil.CATEGORY_LIST);
		if (jsonDate == null || "".equals(jsonDate)) {
			// 2.1 如果Redis中没有,则去关系型数据库中查找
			List<Category> categories = productDao.findAllCategory();
			jsonDate = new Gson().toJson(categories);
			// 2.2同时把数据存入Redis中
			jedis.set(CommonUtil.CATEGORY_LIST, jsonDate);
		}
		// 关闭jedis
		jedis.close();
		// 返回数据
		return jsonDate;
	}
	
	/**
	 * 业务:根据商品的id分页查询
	 * @param cid 
	 * @param pageSize 
	 * @param curPage 
	 * @return
	 */
	public PageBean<Product> getPageBean(String curPageStr, int pageSize, String cidStr) throws Exception {
		// 实例pageBean
		PageBean<Product> pageBean = new PageBean<>();
		// 1. 设置每页显示的数量
		pageBean.setPageSize(pageSize);
		// 2. 设置当前页
		int curPage = 1;
		if (curPageStr != null && !"".equals(curPageStr)) {
			curPage = Integer.valueOf(curPageStr);
		}
		pageBean.setCurPage(curPage);
		// 商品的cid转换
		int cid = 1;
		if (cidStr != null && !"".equals(cidStr)) {
			cid = Integer.valueOf(cidStr);
		}
		// 3. 设置商品总数
		
		int count = productDao.getProductCount(cid);
		pageBean.setCount(count);
		// 1. 获取当前页数据list
		List<Product> list = productDao.findProductByCid(cid, curPage, pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	
	/**
	 * 根据商品的pid获取商品详情
	 * @param pidStr
	 * @return Product
	 */
	public Product findProductByPid(String pidStr) throws Exception{
		// 判断pid的有效性
		int pid = Integer.valueOf(pidStr);
		// 调用数据访问层获取商品的信息
		Map<String, Object> map = productDao.findProductByPid(pid);
		Product product = null;
		// 封装商品详情,要封装一个类别信息
		// 判断返回map的有效性才封装
		if (map !=null && map.size() > 0) {
			product = new Product();
			Category category = new Category();;
			BeanUtils.populate(product, map);
			BeanUtils.populate(category, map);
			product.setCategory(category);
		}
		return product;
	}

}
