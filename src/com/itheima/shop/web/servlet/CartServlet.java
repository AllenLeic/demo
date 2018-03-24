package com.itheima.shop.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.shop.entity.Cart;
import com.itheima.shop.service.CartService;
import com.itheima.shop.util.CommonUtil;

/**
 * 这是处理和购物车相关的请求的servlet
 * @author AllenLei
 * @date 2018年3月20日 下午5:24:22
 */
@WebServlet("/cart")
public class CartServlet extends BaseServlet {
	
	// 实例化购物车的业务类
	private CartService cartService = new CartService();
	
	/**
	 * 处理修改商品数量的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void updateNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 获取传入进来的参数
			int pid = Integer.valueOf(request.getParameter("pid"));
			int num = Integer.valueOf(request.getParameter("number"));
			// 获取会话域中的session中的cart
			Cart cart = (Cart) request.getSession().getAttribute(CommonUtil.CART_SESSION);
			cartService.updateNum(cart, pid ,num);
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 处理清空购物车的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 到了这里直接从会话域中获取购物车然后清空
		request.getSession().setAttribute(CommonUtil.CART_SESSION, null);
		// 重定向
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	/**
	 * 处理根据商品pid删除购物车中对应商品的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 获取商品的pid
			int pid = Integer.valueOf(request.getParameter("pid"));
			// 2. 从会话域中获取购物车信息
			Cart cart = (Cart) request.getSession().getAttribute(CommonUtil.CART_SESSION);
			// 3. 调用业务逻辑层处理删除购物车中商品的业务
			cartService.deleteProduct(cart, pid);
			// 4. 转发到购物车页面
			response.sendRedirect(request.getContextPath() + "/cart.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 处理添加到购物车的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 1. 从请求中获取pid和要购买的数量number
			int number = Integer.valueOf(request.getParameter("number"));
			int pid = Integer.valueOf(request.getParameter("pid"));
			// 2. 获取session中的购物车,如果没有购物车,则新建一个购物车.
			Cart cart = (Cart) request.getSession().getAttribute(CommonUtil.CART_SESSION);
			if (cart == null) {
				cart = new Cart();
				request.getSession().setAttribute(CommonUtil.CART_SESSION, cart);
			}
			/* 3. 调用业务逻辑类获取商品的完整信息,添加入购物车的购物项中.
				因为是引用数据类型而且是存入的session中,所以不用转发.*/
			cartService.addProduct(cart, pid, number);
			// 4. 重定向到购物车页面.
			response.sendRedirect(request.getContextPath() + "/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
