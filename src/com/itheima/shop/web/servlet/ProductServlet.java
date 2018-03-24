package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.shop.entity.PageBean;
import com.itheima.shop.entity.Product;
import com.itheima.shop.service.ProcuctService;
import com.itheima.shop.util.CommonUtil;


/**
 * 商品相关的servlet
 * @author AllenLei
 * @date 2018年3月18日 下午5:13:18
 */
@WebServlet("/product")
public class ProductServlet extends BaseServlet {
	// 实例商品业务逻辑类
	ProcuctService productService = new ProcuctService();
	
	/**
	 * 根据商品的pid获取商品详情
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 获取传入进来的pid
			String pidStr = request.getParameter("pid");
			// 2. 调用业务层处理根据商品pid获取商品详情的业务
			Product product = productService.findProductByPid(pidStr);
			// 3. 获取返回的结果,把结果写入请求域
			request.setAttribute("product", product);
			// 4. 跳转到商品详情页面
			request.getRequestDispatcher("product_info.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			// 跳转到友好页面
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据商品的cid分页查询所有的商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAllProductByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 获取传入的数据
			String curPage = request.getParameter("curPage");
			int pageSize = 6;
			String cid = request.getParameter("cid");
			// 1. 调用业务层获取 pageBean
			PageBean<Product> pageBean = productService.getPageBean(curPage, pageSize, cid);
			// 2. 将pageBean放入请求域中
			request.setAttribute("pageBean", pageBean);
			// 3. 转发到product_list
			request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 获取所有商品分类列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 调用业务层获取商品类别的信息
			String jsonData = productService.findAllCategory();

			response.getWriter().write(jsonData);
			
		} catch (Exception e) {
			e.printStackTrace();
			// 给用户发送错误信息的提示
			CommonUtil.sendAjaxErrorMsg(response, "服务器跑火星去啦,请稍后再试");
		}
	}
	/**
	 * 跳转首页的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void toIndexJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1.调用业务层获取首页的数据
			// 1.1  热门商品数据
			List<Product> hotProduct = productService.findHotProduct();
			// 1.2 最新商品的数据
			List<Product> newProduct = productService.findNewProduct();
			// 2.将数据写入到请求域中
			request.setAttribute("hotProduct", hotProduct);
			request.setAttribute("newProduct", newProduct);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			// 3.转发到index.jsp页面
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
