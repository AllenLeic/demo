package com.itheima.shop.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.shop.entity.Cart;
import com.itheima.shop.entity.Order;
import com.itheima.shop.entity.User;
import com.itheima.shop.service.OrderService;
import com.itheima.shop.util.CommonUtil;


/**
 * 这是处理订单请求的servlet
 * Servlet implementation class OrderServlet
 */
@WebServlet(description = "处理订单的servlet", urlPatterns = { "/order" })
public class OrderServlet extends BaseServlet {
	
	// 实例业务逻辑类
	private OrderService orderService = new OrderService();
	
	
			
	
	/**
	 * 处理确认订单的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// 1. 获取订单页面传过来的数据
		try {
			String address = request.getParameter("address");
			String name = request.getParameter("name");
			String telephone = request.getParameter("telephone");
			HttpSession session = request.getSession();
			Order order = (Order) session.getAttribute(CommonUtil.ORDER_SESSION);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			// 2. 调用业务层处理写入数据库的业务。
			boolean flag = orderService.confirmOrder(order);
			if (flag) {
				// 清空购物车
				session.removeAttribute(CommonUtil.CART_SESSION);
				orderService.updateOrderState(order.getOid());	
				response.sendRedirect(request.getContextPath() + "/confirm_ok.jsp");
				//5.更新支付状态
				
			} else {
				out.write("<script>alert('服务器繁忙,订单生成失败');history.back();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("<script>alert('服务器繁忙,订单生成失败');history.back();</script>");
			throw new RuntimeException();
		}
	}

	
	
	/**
	 *  处理跳查询用户订单的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void toOrderListJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 从会话域中获取用户信息。
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute(CommonUtil.USER_LOGINUSER);
			// 调用业务层获取个人全部订单信息
			List<Order> orderList = orderService.getOrderListByID(loginUser);
			// 把订单集合存到会话域中。
			request.setAttribute("orderList", orderList);
			request.getRequestDispatcher("order_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 *  处理跳转到订单页面的请求
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void toOrderInfoJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 接收从购物车页面过来的请求
			// 因为购物车数据在会话域中，所以，从会话域中取出cart和用户
			HttpSession session = request.getSession();
			User loginUser = (User) session.getAttribute(CommonUtil.USER_LOGINUSER);
			Cart cart = (Cart)session.getAttribute(CommonUtil.CART_SESSION);
			// 2.调用业务层处理业务 
			Order order = orderService.toOrderInfoJsp(cart, loginUser);
			// 3.将信息存储到session中
			session.setAttribute(CommonUtil.ORDER_SESSION, order);
			// 4. 跳转到order_info 页面
			response.sendRedirect(request.getContextPath() + "/order_info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
